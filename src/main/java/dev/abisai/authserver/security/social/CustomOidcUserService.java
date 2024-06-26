package dev.abisai.authserver.security.social;

import dev.abisai.authserver.security.social.mapper.OidcUserMapper;
import dev.abisai.authserver.model.User;
import dev.abisai.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final UserService userService;
    private final Map<String, OidcUserMapper> mappers;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Assert.isTrue(mappers.containsKey(registrationId), "No mapper defined for such registrationId");
        OidcUserMapper mapper = mappers.get(userRequest.getClientRegistration().getRegistrationId());
        String email = userRequest.getIdToken().getEmail();
        User localUser = userService.getByUsername(email);

        if (localUser != null) {
            return mapper.map(oidcUser.getIdToken(), oidcUser.getUserInfo(), localUser);
        }

        //Map unregistered user
        return mapper.map(oidcUser);
    }
}