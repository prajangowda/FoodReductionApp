package com.prajan.FoodReduction.authService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prajan.FoodReduction.DTO.LoginResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2SuccessHandle implements AuthenticationSuccessHandler {

    @Autowired
    private OauthService oauthService;

    private  final ObjectMapper objectMapper;


    public Oauth2SuccessHandle(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User=(OAuth2User) authentication.getPrincipal();

       String registrationId=token.getAuthorizedClientRegistrationId();

        LoginResponse loginResponseDto = oauthService.handleOauthLoginRequest(oauth2User, registrationId);

        // Write JSON directly to the response
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponseDto));
        response.getWriter().flush();
    }
}
