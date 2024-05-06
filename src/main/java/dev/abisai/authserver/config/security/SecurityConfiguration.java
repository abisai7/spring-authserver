package dev.abisai.authserver.config.security;

import dev.abisai.authserver.config.security.social.SocialLoginAuthenticationSuccessHandler;
import dev.abisai.authserver.config.security.social.UserServiceOAuth2UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          AuthenticationSuccessHandler authenticationSuccessHandler
    ) throws Exception {
        return http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .anyRequest().authenticated()
//                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                )
                .formLogin(withDefaults())
                .oauth2Login(oauth ->
                        oauth
                                .successHandler(authenticationSuccessHandler)
                )
                .logout((logout) -> logout.permitAll())
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(UserServiceOAuth2UserHandler handler) {
        SocialLoginAuthenticationSuccessHandler authenticationSuccessHandler =
                new SocialLoginAuthenticationSuccessHandler();
        authenticationSuccessHandler.setOidcUserHandler(handler);
        return authenticationSuccessHandler;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("admin")
//
//                // {noop} means "no operation," i.e., a raw password without any encoding applied.
//                .password("{noop}secret")
//
//                .roles("ADMIN")
//                .authorities("ARTICLE_READ", "ARTICLE_WRITE")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}