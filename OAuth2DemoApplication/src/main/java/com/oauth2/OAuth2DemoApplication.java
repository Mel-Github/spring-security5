package com.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;

//@SpringBootApplication
//public class OAuth2DemoApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(OAuth2DemoApplication.class, args);
//	}
//

//}

/*
@RestController
@SpringBootApplication
public class OAuth2DemoApplication {

//	@Value("#{ @environment['spring.security.oauth2.resource.server'] }")
//	private String resourceServerUrl;

	@Value("#{ @environment['okta.oauth2.audience'] }")
	private String resourceServerUrl;

	private WebClient webClient;

	public OAuth2DemoApplication(WebClient webClient) {
		this.webClient = webClient;
	}

	public static void main(String[] args) {

		SpringApplication.run(OAuth2DemoApplication.class, args);
	}

	@GetMapping("/")
	String home(@AuthenticationPrincipal OidcUser user) {

		return "Hello " + user.getFullName();
	}

	@GetMapping("/api")
	String api() {
		System.out.println("The resourceServer is " + resourceServerUrl);
		String webClientString = webClient.get().uri(resourceServerUrl + "/api").retrieve().bodyToMono(String.class).block();
		System.out.println("webClientString is " + webClientString);
		return this.webClient
				.get()
				.uri(this.resourceServerUrl + "/api")
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}


//	@RequestMapping("/userinfo")
//	public String userinfo(Model model, OAuth2AuthenticationToken authentication) {
//		OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
//		Map userAttributes = Collections.emptyMap();
//		String userInfoEndpointUri = authorizedClient.getClientRegistration()
//				.getProviderDetails().getUserInfoEndpoint().getUri();
//		if (!StringUtils.isEmpty(userInfoEndpointUri)) {    // userInfoEndpointUri is optional for OIDC Clients
//			userAttributes = WebClient.builder()
//					.filter(oauth2Credentials(authorizedClient)).build()
//					.get().uri(userInfoEndpointUri)
//					.retrieve()
//					.bodyToMono(Map.class).block();
//		}
//		model.addAttribute("userAttributes", userAttributes);
//		return "userinfo";
//	}

	@Configuration
	public static class OktaWebClientConfig {

		@Bean
		WebClient webClient(ClientRegistrationRepository clientRegistrations, OAuth2AuthorizedClientRepository authorizedClients)
		{
			ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
					new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
			oauth2.setDefaultOAuth2AuthorizedClient(true);
			oauth2.setDefaultClientRegistrationId("okta");
			return WebClient.builder()
					.apply(oauth2.oauth2Configuration())
					.build();
		}
	}
}*/


@RestController
@SpringBootApplication
public class OAuth2DemoApplication {

	@Value("#{ @environment['spring.security.oauth2.resource.server'] }")
	private String resourceServerUrl;

	private WebClient webClient;

	public OAuth2DemoApplication(WebClient webClient) {
		this.webClient = webClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(OAuth2DemoApplication.class, args);
	}

	@GetMapping("/")
	String home(@AuthenticationPrincipal OidcUser user) {
		return "Hello " + user.getFullName();
	}

	@GetMapping("/api")
	String api() {
		return this.webClient
				.get()
				.uri(this.resourceServerUrl + "/api")
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	@Configuration
	public static class OktaWebClientConfig {

		@Bean
		WebClient webClient(
				ClientRegistrationRepository clientRegistrations,
				OAuth2AuthorizedClientRepository authorizedClients
		) {
			ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
					new ServletOAuth2AuthorizedClientExchangeFilterFunction(
							clientRegistrations, authorizedClients
					);
			oauth2.setDefaultOAuth2AuthorizedClient(true);
			oauth2.setDefaultClientRegistrationId("okta");
			return WebClient.builder()
					.apply(oauth2.oauth2Configuration())
					.build();
		}
	}
}