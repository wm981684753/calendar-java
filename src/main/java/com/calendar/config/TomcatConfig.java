package com.calendar.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SSL部署配置，同时开放http和https访问
 */
@Configuration
public class TomcatConfig {
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
        return tomcat;
    }

    private Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //同时启用http（8082）、https（8081）两个端口
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(80);
        connector.setRedirectPort(443);
        return connector;
    }
}
