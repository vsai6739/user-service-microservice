package com.userservice.userservice;

import com.userservice.userservice.security.services.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.annotation.Commit;

import java.util.UUID;

@SpringBootTest
class UserserviceApplicationTests {
	@Autowired
	private JpaRegisteredClientRepository jpaRegisteredClientRepository;

	@Test
	void contextLoads() {
	}
	@Test
	@Commit
	public void registerClientDetails(){
		        RegisteredClient productserviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("product-service")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/browser-callback")
                 .postLogoutRedirectUri("https://oauth.pstmn.io/v1/browser-callback")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("CUSTOMER")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

				jpaRegisteredClientRepository.save(productserviceClient);
	}

}
