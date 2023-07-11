package com.example.security.controller;

import com.example.security.entity.AuthDTO;
import com.example.security.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class OAuthController {
    private final OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient;
    private final CustomOAuth2UserService oauthUserService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public OAuthController(OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient,
                           CustomOAuth2UserService oauthUserService,
                           ClientRegistrationRepository clientRegistrationRepository) {
        this.accessTokenResponseClient = accessTokenResponseClient;
        this.oauthUserService = oauthUserService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @PostMapping
    public ResponseEntity<?> handleAuthorizationCode(@RequestBody AuthDTO authDTO) {
        ClientRegistration naverRegistration = clientRegistrationRepository.findByRegistrationId("naver");

        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
                .clientId(naverRegistration.getClientId())
                .authorizationUri(naverRegistration.getProviderDetails().getAuthorizationUri())
                .redirectUri(naverRegistration.getRedirectUri())
                .scopes(naverRegistration.getScopes())
                .state(authDTO.getState())
                .build();

        OAuth2AuthorizationResponse authorizationResponse = OAuth2AuthorizationResponse.success(authDTO.getCode())
                .redirectUri(naverRegistration.getRedirectUri())
                .state(authDTO.getState())
                .build();

        OAuth2AuthorizationExchange authorizationExchange = new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse);
        OAuth2AuthorizationCodeGrantRequest grantRequest = new OAuth2AuthorizationCodeGrantRequest(naverRegistration, authorizationExchange);
        OAuth2AccessTokenResponse tokenResponse = accessTokenResponseClient.getTokenResponse(grantRequest);
        OAuth2UserRequest userRequest = new OAuth2UserRequest(naverRegistration, tokenResponse.getAccessToken());
        OAuth2User oauthUser = oauthUserService.loadUser(userRequest);
        return ResponseEntity.ok(oauthUser);
    }
}