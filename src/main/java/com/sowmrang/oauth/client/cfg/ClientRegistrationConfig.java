package com.sowmrang.oauth.client.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.security.Provider;
import java.util.*;


@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
public record ClientRegistrationConfig(String registrationId,String clientId,String clientSecret,String[] scopes) {

    public ClientRegistration asClientRegistration(ProviderDetailConfig config) {

        return ClientRegistration.withRegistrationId(registrationId)
                .clientId(clientId).clientSecret(clientSecret)
                .scope(scopes)
                .tokenUri(config.tokenUri())
                .authorizationUri(config.authorizationUri())
                .jwkSetUri(config.jwkSetUri())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).build();
    }
}
