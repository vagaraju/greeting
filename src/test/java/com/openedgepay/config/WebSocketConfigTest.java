package com.openedgepay.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.openedgepay.client.TestWebSocketClient;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebSocketConfigTest {

  @Value("${config.hostname}")
  private String hostname;

  @Value("${config.port}")
  private String port;

  private String wsUrl;
  private TextMessage message;
  TestWebSocketClient wsClient;
  private TextMessage ogMsg;
  @Mock
  private WebSocketSession session;

  @Autowired
  private TxnStatusHandler txnStatusHandler;

  @Before
  public void setObjects() throws URISyntaxException {
    wsUrl = "ws://localhost:8080/wss-mvp/TxnStatus/JS?Token=JvMy0OOD05s77oxZdTc2w48KwnPqrdKZ&TerminalID=456&OrderID789&XwebID=1011";
    message = new TextMessage("{\n" + "  \"OrderID\": \"sale\",\n" + "  \"TerminalID\": \"91827364\",\n"
        + "  \"RequestStatus\":\"open\"\n" + "}");
    String strGatewayResponse = "{\"GatewayResponse\":{\"OrderID\":\"sale\",\"TerminalID\":\"91827364\"}}";
    ogMsg = new TextMessage(strGatewayResponse);
  }

  @Test
  public void handleTextMessageSuccess() throws IOException {
    session = Mockito.mock(WebSocketSession.class);
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "JS");
    Mockito.when(session.getAttributes()).thenReturn(mockMap);
    txnStatusHandler.handleTextMessage(session, message);
    assertTrue(txnStatusHandler.getConMapSession().containsKey("sale91827364"));
  }

  @Test
  public void handleAsynchTextMessage() throws IOException {
    session = Mockito.mock(WebSocketSession.class);
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "OG");

    Mockito.when(session.getAttributes()).thenReturn(mockMap);

    txnStatusHandler.handleTextMessage(session, ogMsg);

    WebSocketSession sessionForwarded = txnStatusHandler.getConMapSession().get("sale91827364");
    assertEquals("testing send message: needs to improve this", null, sessionForwarded);
  }

  @Test
  public void sendGatewayMessageFirst() throws IOException {
    session = Mockito.mock(WebSocketSession.class);
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "OG");
    Mockito.when(session.getAttributes()).thenReturn(mockMap);

    txnStatusHandler.handleTextMessage(session, ogMsg);
    assertFalse(txnStatusHandler.getConMapSession().containsKey("sale91827364"));
  }
  
  @Test
  public void sessionNotAuthenticated() throws IOException {
    session = Mockito.mock(WebSocketSession.class);
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "JS");
    mockMap.put("unauthorized", true);
    Mockito.when(session.getAttributes()).thenReturn(mockMap);
    assertFalse(txnStatusHandler.isSessionAuthenticated(session));
  }
  
  @Test
  public void sessionAuthenticated() throws IOException {
    session = Mockito.mock(WebSocketSession.class);
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "JS");
    mockMap.put("unauthorized", false);
    Mockito.when(session.getAttributes()).thenReturn(mockMap);
    assertTrue(txnStatusHandler.isSessionAuthenticated(session));
  }
  
}
