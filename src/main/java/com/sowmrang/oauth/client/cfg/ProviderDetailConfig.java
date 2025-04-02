package com.sowmrang.oauth.client.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.keycloak")
public class ProviderDetailConfig {
    private String authorizationUri;
    private String tokenUri;
    private String jwtSetUri;

}
