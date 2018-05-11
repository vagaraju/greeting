package com.openedgepay.config;

import static com.openedgepay.constants.Constants.HANDSHAKESOURCE;
import static com.openedgepay.constants.Constants.JS;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.openedgepay.response.RspData;
import com.openedgepay.util.MDCLog;

/**
 * handles all web socket incoming requests.
 *
 * @author M1034465
 *
 */
@Component
public final class TxnStatusHandler extends TextWebSocketHandler {

  /**
   * stored web socket session for future tracking purpose.
   */
  private final ConcurrentHashMap<String, WebSocketSession> conMapSession =
      new ConcurrentHashMap<String, WebSocketSession>();
  /**
   * Gateway and Mobile responses stored here when respective JSclient is not in
   * the picture.
   */
  private final ConcurrentHashMap<String, String> responsesMap = new ConcurrentHashMap<String, String>();
  /**
   * to store request start time.
   */
  private ConcurrentHashMap<String, String> rqstExecTime = new ConcurrentHashMap<String, String>();
  /**
   * to track logging.
   */
  private final transient Logger logger = LoggerFactory.getLogger(TxnStatusHandler.class);

  /**
   * message input handler.
   *
   * @throws IOException
   */
  @Override
  public void handleTextMessage(final WebSocketSession session, final TextMessage message) throws IOException {
    new MDCLog().setMDC(session);
    final String handShakeSource = (String) session.getAttributes().get(HANDSHAKESOURCE);
    logger.info("Msg Received from " + handShakeSource + "request=" + message.getPayload());
    if (JS.equals(handShakeSource)) {
      logger.info(MDCLog.getJsonLogMsg("incomingReq", message.getPayload(), true));
      handleTransactionRequest(session, message);
    } else if (!JS.equals(handShakeSource)) {
      handleTransactionResponse(session, message, handShakeSource);
    }
    new MDCLog().removeMDC();
  }

  /**
   *
   * @param session
   *          The websocket session (JS Client) that delivers the message.
   * @param message
   *          payload for WS server to store data regarding
   */
  public void handleTransactionRequest(final WebSocketSession session, final TextMessage message) {
    String sessionKey = RspData.getKey(new JSONObject(message.getPayload()));
    rqstExecTime.put(sessionKey, "" + session.getAttributes().get("RQST_START_TIME"));
    putSession(sessionKey, session);
    logger.info("JSclient session stored:" + sessionKey);
    if (!(responsesMap.isEmpty()) && responsesMap.containsKey(sessionKey)) {
      logger.info("JSClient received stored response: " + responsesMap.get(sessionKey));
      sendMessage(sessionKey, responsesMap.get(sessionKey));
      responsesMap.remove(sessionKey);
    }
    // Only to return mock response to JS clients.
    // Not essential for PROD environment.
    else if(sessionKey.contains("loadTest")) {
      sendMessage(sessionKey, "Test Response Message for load test.");
      responsesMap.remove(sessionKey);
    }
  }

  /**
   *
   * @param session
   *          The websocket session (Mobile and Gateway Clients) that delivers
   *          the message.
   * @param message
   *          payload
   * @param handShakeSource
   *          where the client comes from (Browser, Mobile, Gateway)
   */
  public void handleTransactionResponse(final WebSocketSession session, final TextMessage message,
      final String handShakeSource) {
    String key = RspData.getKey(message.getPayload(), handShakeSource);
    String rspSysExecTime = MDCLog.getExecTimeinMilliSecs(rqstExecTime.get(key));
    RspData.getJSRsp(message.getPayload(), handShakeSource).forEach((sessionKey, rspMsg) -> {
      if (conMapSession.containsKey(sessionKey)) {
        logger.info("Response received and sent to client: " + sessionKey);
        sendMessage(sessionKey, rspMsg);
        conMapSession.remove(sessionKey);
      } else {
        logger.info("Response stored for collection after JSclient connects: " + sessionKey);
        responsesMap.put(sessionKey, rspMsg);
      }
    });
    JSONObject exec = new JSONObject();
    exec.put("execTime", MDCLog.getExecTimeinMilliSecs(rqstExecTime.get(key)));
    exec.put("execType", session.getAttributes().get(HANDSHAKESOURCE));
    exec.put("RspSysExecTime", rspSysExecTime);
    logger.info(exec.toString());
    rqstExecTime.remove(key);
  }

  /**
   * sends the message to the respective JS component.
   *
   * @param sessionKey
   *          .
   * @param jsResponse
   *          .
   */
  private void sendMessage(final String sessionKey, final String jsResponse) {
    final WebSocketSession jsSession = conMapSession.get(sessionKey);
    logger.info("session Key" + sessionKey);
    logger.info(MDCLog.getJsonLogMsg("outgoingResp", jsResponse, false));
    if (null != jsSession) {
      try {
        jsSession.sendMessage(new TextMessage(jsResponse));
        logger.info("Message sent successfully for " + sessionKey);
      } catch (Throwable e) {
        logger.error("Exception while sending data to the JS component." + e.fillInStackTrace());
      }
    } else {
      logger.info("JSClient session is not active to deliver the message.");
    }
  }

  /**
   * remove session value based on order id and terminal id.
   *
   * @param session
   *          session
   * @param status
   *          status
   * @throws IOException
   *           .
   */
  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception,
      IOException {
    conMapSession.values().remove(session);
    logger.info(session.getAttributes().get(HANDSHAKESOURCE) + " Connection Closed.");
  }

  /**
   * remove session value based on order id and terminal id.
   *
   * @param session
   *          session
   */
  @Override
  public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
    logger.info(session.getAttributes().get(HANDSHAKESOURCE) + " Connection established successfully !!!");
    isSessionAuthenticated(session);
  }

  /**
   * remove session value based on order id and terminal id.
   *
   * @param sessionKey
   *          sessionKey
   * @param session
   *          session
   */
  private void putSession(final String sessionKey, final WebSocketSession session) {
    logger.info("session saved to the map with value =" + sessionKey + ",session: " + session.toString());
    conMapSession.putIfAbsent(sessionKey, session);
  }

  /**
   * checks the authentication status from the WebSocket session attributes that
   * were passed from the HTTP session from the opening handshake.
   *
   * @param session
   *          the websocket session
   */
  public boolean isSessionAuthenticated(final WebSocketSession session) throws IOException {
    boolean authenticated = true;
    if ((boolean) session.getAttributes().get("unauthorized")) {
      session.close(new CloseStatus(4001, " HTTP Authentication failed; no valid credentials available"));
      authenticated = false;
    } else {
      session.sendMessage(new TextMessage("HTTP Authentication Succeeded"));
    }
    return authenticated;
  }

  /**
   * Provides the map with all the clients currently connected to the WebSocket
   * server.
   *
   * @return ConcurrentHashMap
   */
  public ConcurrentHashMap<String, WebSocketSession> getConMapSession() {
    return conMapSession;
  }
}
