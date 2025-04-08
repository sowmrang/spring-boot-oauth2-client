package com.sowmrang.oauth.client;

import com.sowmrang.oauth.client.cfg.ClientRegistrationConfig;
import com.sowmrang.oauth.client.cfg.OAuth2ClientConfig;
import com.sowmrang.oauth.client.cfg.ProviderDetailConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;
import java.net.URI;

@Import(OAuth2ClientConfig.class)
@SpringBootApplication
@Slf4j
@EnableConfigurationProperties({ProviderDetailConfig.class, ClientRegistrationConfig.class})
public class Client implements CommandLineRunner {

    private final OAuth2AuthorizedClientManager oauth2Client;
    private static final String CLIENT_REGN_ID = "keycloak";
    private static final String REQUEST_URI = "http://localhost:9000/";

    public Client(
            @Qualifier("oauthClientManager") OAuth2AuthorizedClientManager client) {
        this.oauth2Client = client;
    }

    public static void main(String[] args) {
        log.info("Initializing client...");
        SpringApplication.run(Client.class);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Initiating a request to instantiate the file upload project...");
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                OAuth2AuthorizedClient client = oauth2Client.
                        authorize(OAuth2AuthorizeRequest.withClientRegistrationId(CLIENT_REGN_ID)
                                .principal("anonymous")
                                .build());
                Request.Builder requestBuilder = chain.request().newBuilder();
                String tokenValue = null;
                if (client == null) {
                    log.error("Unknown error with OAuth client. Client is null");
                    throw new RuntimeException("Unknown error. client is null");
                } else tokenValue = client.getAccessToken().getTokenValue();
                log.info("Access token obtained successfully");
                if (tokenValue != null) requestBuilder.addHeader("Authorization", tokenValue);
                return chain.proceed(requestBuilder.build());
            }
        }).build();
        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(httpClient);
        ClientHttpResponse response = requestFactory.createRequest(URI.create(REQUEST_URI), HttpMethod.GET).execute();
        response.close();
    }
}
