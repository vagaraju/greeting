package com.openedgepay.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * to validate authenticate token against authenticate component.
 *
 * @author M1034465
 *
 */
@Component
public final class AuthComponentService {
  /** to track log statements. */
  private static final Logger LOG = LoggerFactory.getLogger(AuthComponentService.class);

  /** CONTENT_LENGTH for content. */
  private static final int CONTENT_LENGTH = 164;

  /**
   * Url for Auth Component defined by application.properties.
   */
  @Value("${config.authUrl}")
  private String authUrl;
  /**
   * RestTemplateBuilder class is used to create RestTemplate .
   */
  @Autowired
  private RestTemplateBuilder restTemplate;
  /** constructor. */
  public AuthComponentService() {
  }

    /**
     * validate authtoken against authenticate component.
     *
     * @param authToken
     *            authToken generated on client side used for auth component.
     * @param requestId
     *  also used for authentication. A concatenation of orderId,terminalId, and timestamp.
     * @return boolean .
     */

  public boolean isValidAuthToken(final String authToken, final String requestId) {
    boolean isValidAuthToken = false;
    if (null != authToken) {
      final JSONObject authRqst = new JSONObject();
      // set headers.
      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setContentLength(CONTENT_LENGTH);
      // set body information.
      authRqst.put("requestId", requestId);
      authRqst.put("type", "AUTHTOKEN");
      authRqst.put("credential", authToken);
      final HttpEntity<String> entity = new HttpEntity<String>(authRqst.toString(), headers);
      if (LOG.isInfoEnabled()) {
          LOG.info("input=" + entity);
          LOG.info("authUrl=" + authUrl);
        }
      final JSONObject rspJSON = callAuthComponent(entity);
        if (null != rspJSON && null != rspJSON.get("platformToken")) {
            isValidAuthToken = true;
        } else {
            LOG.error("authcomponent output=" + rspJSON);
        }
    }
    return isValidAuthToken;
  }

  /**
   *
   * @param entity
   *          .
   * @return JSONObject
   */
    private JSONObject callAuthComponent(final HttpEntity<String> entity) {
        JSONObject rspJSON = null;
        try {
            rspJSON = new JSONObject(restTemplate.build()
                    .exchange(authUrl, HttpMethod.POST, entity, String.class)
                    .getBody());
        } catch (RestClientException e) {
            LOG.error("callAuthComponent=" + e, e.fillInStackTrace());
        }
        return rspJSON;
    }
}
