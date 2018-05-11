package com.openedgepay.client;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class TestWebSocketClient {

  public TestWebSocketClient(URI endpointURI) {
    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      container.connectToServer(this, endpointURI);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private Session userSession = null;
  public Session getUserSession() {
    return userSession;
  }

  private MessageHandler messageHandler;
  
  @OnOpen
  public void onOpen(Session userSession) {
    System.out.println("client: opening websocket ");
    this.userSession = userSession;
  }

  /**
   * Callback hook for Connection close events.
   *
   * @param userSession
   *          the userSession which is getting closed.
   * @param reason
   *          the reason for connection close
   */
  @OnClose
  public void onClose(Session userSession, CloseReason reason) {
    System.out.println("client: closing websocket");
    this.userSession = null;
  }

  /**
   * Callback hook for Message Events. This method will be invoked when a client
   * send a message.
   *
   * @param message
   *          The text message
   */
  @OnMessage
  public void onMessage(String message) {
    System.out.println("client: received message " + message);
    if (this.messageHandler != null) {
      this.messageHandler.handleMessage(message);
    }
  }

  /**
   * register message handler
   *
   * @param msgHandler the handler to be added dealing with messages
   */
  public void addMessageHandler(MessageHandler msgHandler) {
    this.messageHandler = msgHandler;
  }

  /**
   * Send a message.
   *
   * @param message String payload 
   */
  public void sendMessage(String message) {
    System.out.println("client sending the message...");
    try {
      this.userSession.getAsyncRemote().sendText(message);
    } catch (Exception e) {
      System.out.println("exception occured while sending messgae=" + e);
    }

  }

  public static interface MessageHandler {

    public void handleMessage(String message);
  }

}
