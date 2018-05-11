package com.openedgepay.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.awt.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHandler;

import clover.com.google.common.base.Verify;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebSocketHandshakeInterceptorTest {

  @Autowired
  private WebSocketHandShakeInterceptor webSocketHandshakeInterceptor;
  
  @Mock
  private ServletServerHttpRequest httpRequest;
  @Mock
  private ServerHttpResponse httpResponse;
  @Mock
  private WebSocketHandler wsHandler;
  @Mock
  private Map<String, Object> attributesMap;
  @Mock
  private Exception exception;
  private URI jsPath;
  private URI failJSPath;
  private URI mbPath;
  private String mbToken;
  private String terminalId;
  @Before
  public void setObjects() throws URISyntaxException {
    jsPath = new URI("/wss-mvp/TxnStatus/JS?Token=JvMy0OOD05s77oxZdTc2w48KwnPqrdKZ&RequestId=12345");
    failJSPath = new URI("/wss-mvp/TxnStatus/JS?Token=JvMy0OOD05s77oxZdTc2w48KwnPqrdKZfail&RequestId=12345");
    mbPath = new URI("/wss-mvp/TxnStatus/MB");
    mbToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZXJjaGFudElkIjoiOTA2MDAwMDc1MTUyIiwidGVybWluYWxJZCI6IjkwMDMyODA3IiwidHlwZSI6IkFVVEhUT0tFTiIsInRpbWVzdGFtcCI6IjE1MTY5ODQ0NDY4MDgifQ==.+B5rE4xuCfDUprapYYOTcLPKIyiTYv1gKEsNYIxh9KE=";
    terminalId = "90032807";
  }
  
  @After
  public void destroyObjects() {
    attributesMap.clear();
  }
  
  @Test
  public void handshakeSucceeds() throws Exception {
    httpRequest = Mockito.mock(ServletServerHttpRequest.class);
    httpResponse = Mockito.mock(ServerHttpResponse.class);
    wsHandler = Mockito.mock(WebSocketHandler.class);
    
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "JS");
    Mockito.when(attributesMap.get("HandshakeRequestSource")).thenReturn(mockMap.get("HandshakeRequestSource"));
    Mockito.when(httpRequest.getURI()).thenReturn(jsPath);
    assertTrue(webSocketHandshakeInterceptor.beforeHandshake(httpRequest, httpResponse, wsHandler, attributesMap));
  }
  
  @Test
  public void handshakeSucceedsMB() throws Exception {
    HttpHeaders testHeaders = new HttpHeaders();
    httpRequest = Mockito.mock(ServletServerHttpRequest.class);
    httpResponse = Mockito.mock(ServerHttpResponse.class);
    wsHandler = Mockito.mock(WebSocketHandler.class);
    ArrayList<String> connection = new ArrayList<String>();
    Map<String, Object> mockMap = new HashMap<String, Object>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDDHHmmss.SSS");
    connection.add("upgrade");
    String requestId = "testOrderId123-" + terminalId + "-" + dateFormat.format(Calendar.getInstance().getTime());
    testHeaders.add("Token", mbToken);
    testHeaders.add("RequestId", requestId);
    testHeaders.setConnection(connection);
    testHeaders.setUpgrade("websocket");
    mockMap.put("HandshakeRequestSource", "MB");
    Mockito.when(attributesMap.get("HandshakeRequestSource")).thenReturn(mockMap.get("HandshakeRequestSource"));
    Mockito.when(httpRequest.getURI()).thenReturn(mbPath);
    Mockito.when(httpRequest.getHeaders()).thenReturn(testHeaders);
    verify(httpResponse, never()).setStatusCode(HttpStatus.UNAUTHORIZED);
  }
  @Test
  public void handshakeFails() throws Exception {
    httpRequest = Mockito.mock(ServletServerHttpRequest.class);
    httpResponse = Mockito.mock(ServerHttpResponse.class);
    wsHandler = Mockito.mock(WebSocketHandler.class);
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "InvalidValue");
    Mockito.when(httpRequest.getURI()).thenReturn(failJSPath);
    Mockito.when(attributesMap.get("HandshakeRequestSource")).thenReturn("InvalidValue");
    assertFalse(webSocketHandshakeInterceptor.beforeHandshake(httpRequest, httpResponse, wsHandler, attributesMap));
  } 

  @Test
  public void handshakeAuthValidationSucceeds() throws Exception {
    httpRequest = Mockito.mock(ServletServerHttpRequest.class);
    httpResponse = Mockito.mock(ServerHttpResponse.class);
    wsHandler = Mockito.mock(WebSocketHandler.class);
    exception = null;
    Map<String, Object> mockMap = new HashMap<String, Object>();
    mockMap.put("HandshakeRequestSource", "JS");
    Mockito.when(attributesMap.get("HandshakeRequestSource")).thenReturn(mockMap.get("HandshakeRequestSource"));
    Mockito.when(httpRequest.getURI()).thenReturn(jsPath);
    webSocketHandshakeInterceptor.afterHandshake(httpRequest, httpResponse, wsHandler, exception);
    assertEquals("Auth component validation Successful:",null, exception);
  }
  @Test
  public void getHandShakeTestForJS() {
    String actual = webSocketHandshakeInterceptor.getHandshakeValue("/wss-mvp/TxnStatus/JS?Token=JvMy0OOD05s77oxZdTc2w48KwnPqrdKZ&RequestId=12345");
    assertEquals("handshake source check:","JS", actual);
  } 
  @Test
  public void getHandShakeTestForOG() {
    String actual = webSocketHandshakeInterceptor.getHandshakeValue("/wss-mvp/TxnStatus/OG");
    assertEquals("handshake source check:","OG", actual);
  } 
  @Test
  public void getHandShakeTestForMB() {
    String actual = webSocketHandshakeInterceptor.getHandshakeValue("/wss-mvp/TxnStatus/MB");
    assertEquals("handshake source check:","MB", actual);
  } 
  
}
