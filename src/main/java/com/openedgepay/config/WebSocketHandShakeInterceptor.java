package com.openedgepay.config;

import static com.openedgepay.constants.Constants.ALLOWEDORIGINS;
import static com.openedgepay.constants.Constants.HANDSHAKESOURCE;
import static com.openedgepay.constants.Constants.JS;
import static com.openedgepay.constants.Constants.JS_WSS_URI;
import static com.openedgepay.constants.Constants.MB;
import static com.openedgepay.constants.Constants.MB_WSS_URI;
import static com.openedgepay.constants.Constants.OG;
import static com.openedgepay.constants.Constants.OG_WSS_URI;

import java.net.URI;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import com.openedgepay.services.AuthComponentService;

/**
 * handle hand shake requests.
 *
 * @author M1034465
 *
 */
@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {
  /**
   * logger variable to track log statements.
   */
  private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandShakeInterceptor.class);

  /**
   * service which allows for OpenEdge Authentication functionality.
   */
  @Autowired
  private AuthComponentService authComponentService;

  /**
   * constructor function to create the new instance of the class.
   */
  WebSocketHandShakeInterceptor() {
    // gives the new instance of the class.
  }

  /**
   * A method that handles the authenticate component request.
   *
   * @param request
   *          request.
   * @param response
   *          response object.
   * @param wsHandler
   *          handler.
   * @param attributes
   *          to store the attributes to send to handler class.
   * @throws Exception
   *           exception when something fails.
   * @return boolean
   */
  @Override
  public final boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response,
      final WebSocketHandler wsHandler, final Map<String, Object> attributes) throws Exception {
    URI requestURI = request.getURI();
    if (JS.equals(getHandshakeValue(requestURI.getPath()))) {
      attributes.put("RQST_START_TIME", System.currentTimeMillis());
    }
    boolean isValidConRqst = true;
    boolean unauthorized = false;
    MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(requestURI).build().getQueryParams();
    if (parameters != null && parameters.size() > 0) {
      attributes.put("reqId", parameters.getFirst("RequestId"));
      MDC.put("reqId", parameters.getFirst("RequestId"));
    }
    LOG.info("requestURI.getPath()=" + requestURI.getPath());
    attributes.put(HANDSHAKESOURCE, getHandshakeValue(requestURI.getPath()));
    if (!ALLOWEDORIGINS.contains(attributes.get(HANDSHAKESOURCE))) {
      isValidConRqst = false;
    }

    // JS client passes authToken through query params
    if (JS.equals(getHandshakeValue(request.getURI().getPath())) && !authComponentService.isValidAuthToken(parameters
        .getFirst("Token"), parameters.getFirst("RequestId"))) {
      unauthorized = true;
    } else if (MB.equals(getHandshakeValue(request.getURI().getPath())) && !authComponentService.isValidAuthToken(
        request.getHeaders().getFirst("Token"), request.getHeaders().getFirst("RequestId"))) {
      unauthorized = true;
    }
    attributes.put("unauthorized", unauthorized);
    return isValidConRqst;
  }

  /**
   * A method that handles the auth component request.
   *
   * @param request
   *          request.
   * @param response
   *          response object.
   * @param wsHandler
   *          handler.
   * @param exception
   *          exception when something fails.
   */
  @Override
  public final void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response,
      final WebSocketHandler wsHandler, final Exception exception) {
  }

  /**
   * gives the request origin source values.
   *
   * @param requestURI
   *          .
   * @return String .
   */
  public final String getHandshakeValue(final String requestURI) {
    String handSkake = "";
    if (requestURI != null) {
      if (requestURI.toLowerCase(Locale.ENGLISH).contains(OG_WSS_URI.toLowerCase(Locale.ENGLISH))) {
        handSkake = OG;
      } else if (requestURI.toLowerCase(Locale.ENGLISH).contains(MB_WSS_URI.toLowerCase(Locale.ENGLISH))) {
        handSkake = MB;
      } else if (requestURI.toLowerCase(Locale.ENGLISH).contains(JS_WSS_URI.toLowerCase(Locale.ENGLISH))) {
        handSkake = JS;
      }
    }
    return handSkake;
  }
}
