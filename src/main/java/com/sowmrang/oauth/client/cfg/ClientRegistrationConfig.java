package com.sowmrang.oauth.client.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;


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
