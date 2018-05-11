package com.openedgepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.openedgepay.CORSFilter;

/**
 * time being this was added. class will be used for local testing.
 */
@SpringBootApplication(scanBasePackages = "com.openedgepay")
/**
 * spring boot Application class to test the application in standalone mode.
 *
 * @author M1034465 - Vagaraju Gudeseva
 * @author M1031934 - Eduardo Fonseca
 */
public class Application extends SpringBootServletInitializer {
  /**
   * test spring boot in stand alone mode.
   *
   * @param args
   *          rags.
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
  /**
   * apply the CORS Filter options.
   * @return oFilterRegistrationBean .
   */
  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean oFilterRegistrationBean = new FilterRegistrationBean(new CORSFilter());
      oFilterRegistrationBean.setName("CORS Filter");
      oFilterRegistrationBean.addUrlPatterns("/*");
      oFilterRegistrationBean.setOrder(1);
      return oFilterRegistrationBean;
  }

}
