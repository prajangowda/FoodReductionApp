package com.prajan.FoodReduction.authService;

import com.prajan.FoodReduction.DTO.LoginResponse;
import com.prajan.FoodReduction.enums.provider;
import com.prajan.FoodReduction.model.CustomUserDetails;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.UserInRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTservice {


    @Value(value = "${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey key()
    {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String Gettoken(CustomUserDetails userPrincipal)
    {
        String role = userPrincipal.getRole() != null
                ? userPrincipal.getRole().name()
                : "UNASSIGNED";   // or "NONE"

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())   // use setSubject (standard)
                .claim("role", role) // dynamic roles
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 min
                .signWith(key(), SignatureAlgorithm.HS256) // explicitly define algo
                .compact();


    }

    public String getemailfromtoken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }

    public provider getProviderFromRegistrationId(String registrationId) {

        return switch (registrationId.toLowerCase()) {

            case "google" -> provider.GOOGLE;

//            case "github" -> provider.GITHUB;

            default -> throw new IllegalArgumentException(
                    "Unsupported registrationId: " + registrationId
            );
        };
    }


    public String extractProviderId(OAuth2User oauthUser, String registrationId) {

        String providerId;
        switch (registrationId.toLowerCase()) {
            case "google":
                providerId = oauthUser.getAttribute("sub");
                break;

            case "github":
                Object id = oauthUser.getAttribute("id");
                providerId = (id != null) ? id.toString() : null;
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported registrationId: " + registrationId
                );
        }
        if (providerId == null || providerId.isBlank()) {
            throw new OAuth2AuthenticationException(
                    "Provider ID is missing from OAuth2 response"
            );
        }
        return providerId;
    }


}
