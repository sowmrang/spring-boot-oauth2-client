package com.sowmrang.oauth.client.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;


@Setter
@Getter
public class ClientRegistrationConfig {
    private String id;
    private ClientRegistration clientRegistration;

}
