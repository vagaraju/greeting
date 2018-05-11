package com.openedgepay.services;

import static org.junit.Assert.assertFalse;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * test class for auth component service.
 * 
 * @author M1034465
 *
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthComponentServiceTest {
    /** to track log statements. */
    private static final Logger LOG = LoggerFactory.getLogger(AuthComponentService.class);    
    String authToken = null;
  /**
   * constructor.
   *
   */
  @Autowired
  private AuthComponentService authComponentService;
  
  /**
   * Url for Auth Component defined by application.properties.
   */
  @Value("${config.authUrl}")
  private String authUrl;
  
  public AuthComponentServiceTest() {
    // create new instance for AuthComponentServiceTest.
  }

  /**
   * initialize the required data for the test cases.
   */
  @Before
  public void initObjects() {
    String tokenPayload = "{\"xwebId\":\"906000103556\",\"xwebTerminalId\":\"90030234\",\"type\":\"AUTHTOKEN\"}";
    String strTokenURL = authUrl;
    final RestTemplate restTemplate = new RestTemplate();
    JSONObject rspJSON = null;
    try {
        // set headers.
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> entity = new HttpEntity<String>(tokenPayload, headers);
        
        
        rspJSON = new JSONObject(restTemplate.exchange(strTokenURL,
                HttpMethod.POST, entity, String.class).getBody());
        if(null != rspJSON) {
            authToken = (String) rspJSON.get("authToken");
        }
        
    } catch (RestClientException e) {
        LOG.error("callAuthComponent=" + e);
    }
        
  }
  /**
   * test for validity of the invalid token.
   */
  @Test
  public void testForInvalidToken() {
    // assert statements
    assertFalse(authComponentService.isValidAuthToken("JvMy0OOD05s77oxZdTc2w48KwnPqrdKZaaa", "test"));
  }
  /**
   * test for validity of the invalid token.
   */
  @Test
  public void testForEmptyToken() {
    // assert statements
    assertFalse(authComponentService.isValidAuthToken(null, "test"));
  }
}
