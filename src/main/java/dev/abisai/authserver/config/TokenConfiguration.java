package dev.abisai.authserver.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
public class TokenConfiguration {

    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator(
            JWKSource<SecurityContext> jwkSource,
            OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer
    ) {
        NimbusJwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        accessTokenGenerator.setAccessTokenCustomizer(accessTokenCustomizer);
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();

        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator
        );
    }

    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer () {
        return context -> {
            UserDetails userDetails = null;

            if (context.getPrincipal() instanceof OAuth2ClientAuthenticationToken) {
                userDetails = (UserDetails)context.getPrincipal().getDetails();
            } else if (context.getPrincipal() instanceof AbstractAuthenticationToken) {
                userDetails = (UserDetails)context.getPrincipal().getPrincipal();
            } else {
                throw new IllegalStateException("Unexpected token type");
            }

            if (!StringUtils.hasText(userDetails.getUsername())) {
                throw new IllegalStateException("Bad UserDetails, username is empty");
            }

            context.getClaims()
                    .claim(
                            "authorities",
                            userDetails.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toSet())
                    )
                    .claim(
                            "username", userDetails.getUsername()
                    );
        };
    }

//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
//        return context -> {
//            UserDetails userDetails = null;
//
//            if (context.getPrincipal() instanceof OAuth2ClientAuthenticationToken) {
//                userDetails = (UserDetails) context.getPrincipal().getDetails();
//            } else if (context.getPrincipal() instanceof AbstractAuthenticationToken) {
//                userDetails = (UserDetails) context.getPrincipal().getPrincipal();
//            } else {
//                throw new IllegalStateException("Unexpected token type");
//            }
//
//            if (!StringUtils.hasText(userDetails.getUsername())) {
//                throw new IllegalStateException("Bad UserDetails, username is empty");
//            }
//
//            context.getClaims()
//                    .claim(
//                            "authorities",
//                            userDetails.getAuthorities().stream()
//                                    .map(GrantedAuthority::getAuthority)
//                                    .collect(Collectors.toSet())
//                    )
//                    .claim(
//                            "username", userDetails.getUsername()
//                    );
//        };
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() {
//        KeyPair keyPair = generateRsaKey();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }
//
//    private static KeyPair generateRsaKey() {
//        KeyPair keyPair;
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//        }
//        catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//        return keyPair;
//    }
}