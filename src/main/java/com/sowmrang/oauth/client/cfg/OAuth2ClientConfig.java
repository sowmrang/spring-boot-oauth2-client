package com.sowmrang.oauth.client.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    protected ClientRegistrationRepository clientRegistrationRepository(ClientRegistrationConfig config,ProviderDetailConfig providerDetails) {
        return new InMemoryClientRegistrationRepository(
                config.asClientRegistration(providerDetails));
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
