package com.openedgepay.config;

import static com.openedgepay.constants.Constants.JS_WSS_URI;
import static com.openedgepay.constants.Constants.MB_WSS_URI;
import static com.openedgepay.constants.Constants.OG_WSS_URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * register the web socket handler.
 *
 * @author M1034465
 *
 */
@Component
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  /**
   * maximum allowed size for responses to post for websocket server.
   */
  public static final int MAX_BUFFER_SIZE = 256024;

  /**
   * Handshake Interceptor configured to authenticate requests to the server.
   */
  @Autowired
  private WebSocketHandShakeInterceptor webSocketHandshakeInterceptor;
  /**
   * Timeout value for when there is no activity between client and server.
   */
  @Value("${config.idleTimeout}")
  private int idleTimeout;
  /**
   * set configuration for websocket server. currently times out connection for
   * no activity between client and server
   *
   * @return Bean containing configuration
   */
  @Bean
  public ServletServerContainerFactoryBean createWebSocketContainer() {
    final ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxSessionIdleTimeout(idleTimeout);
    container.setMaxTextMessageBufferSize(MAX_BUFFER_SIZE);
    container.setMaxBinaryMessageBufferSize(MAX_BUFFER_SIZE);
    return container;
  }
  /**
   * register the web socket handler.
   *
   * @param reg
   *          object.
   */
  @Override
  public final void registerWebSocketHandlers(final WebSocketHandlerRegistry reg) {
    reg.addHandler(new TxnStatusHandler(), MB_WSS_URI,
            JS_WSS_URI, OG_WSS_URI).setAllowedOrigins("*")
            .addInterceptors(webSocketHandshakeInterceptor);
  }
}
