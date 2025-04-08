package com.sowmrang.oauth.client.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.keycloak")
public record ProviderDetailConfig(String authorizationUri, String tokenUri, String jwkSetUri) {
}
