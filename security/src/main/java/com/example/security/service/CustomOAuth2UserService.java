package com.example.security.service;

import com.example.security.entity.AuthUserDTO;
import com.example.security.entity.UserEntity;
import com.example.security.jwt.JwtUtil;
import com.example.security.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
public class CustomOAuth2UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public CustomOAuth2UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.restTemplate = new RestTemplate();
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthUserDTO loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String userInfoUri = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (!StringUtils.hasLength(userInfoUri)) {
            OAuth2Error oauth2Error = new OAuth2Error(
                    "missing_user_info_uri",
                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " +
                            userRequest.getClientRegistration().getRegistrationId(),
                    null
            );
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + userRequest.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);
        Map responseAttributes = response.getBody();
        Map userAttributes = (Map) responseAttributes.get("response");

        UserEntity userEntity = userRepository.findByNaverId((String) userAttributes.get("id"));
        AuthUserDTO userDTO = new AuthUserDTO();
        if (userEntity == null) {
            userDTO.setNaverId((String) userAttributes.get("id"));
            userDTO.setName((String) userAttributes.get("name"));
            userDTO.setEmail((String) userAttributes.get("email"));
            userDTO.setToken(jwtUtil.generateToken(userDTO.getNaverId()));
            userDTO.setNew(true);
        } else {
            userDTO.setToken(jwtUtil.generateToken(userEntity.getNaverId()));
            userDTO.setNew(false);
        }
        return userDTO;
    }
}