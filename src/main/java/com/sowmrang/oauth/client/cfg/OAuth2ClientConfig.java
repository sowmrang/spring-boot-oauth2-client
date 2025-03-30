package com.sowmrang.oauth.client.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    protected ClientRegistrationRepository clientRegistrationRepository(ClientRegistrationConfig config) {
        return new InMemoryClientRegistrationRepository(
                ClientRegistration.withClientRegistration(config.getClientRegistration()).build());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
    ClientRegistrationConfig getClientRegistration() {
        return new ClientRegistrationConfig();
    }

    @Bean
    protected OAuth2AuthorizedClientService clientService(ClientRegistrationRepository repository) {
        return new InMemoryOAuth2AuthorizedClientService(
                repository);
    }

    @Bean(name = "oauthClientManager")
    OAuth2AuthorizedClientManager clientManager(ClientRegistrationRepository repository,
                                                OAuth2AuthorizedClientService service) {
        AuthorizedClientServiceOAuth2AuthorizedClientManager manager = new
                AuthorizedClientServiceOAuth2AuthorizedClientManager(repository, service);
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.
                builder().clientCredentials().build();
        manager.setAuthorizedClientProvider(provider);
        return manager;
    }

}
