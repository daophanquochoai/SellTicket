package doctorhoai.learn.proxy_client.security.OAuth2;

import doctorhoai.learn.proxy_client.business.user.model.request.Provider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GitHubOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {
    @Override
    public CustomUserDetails extractUserInfo(OAuth2User oAuth2User) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(retrieveAttr("login", oAuth2User));
        customUserDetails.setName(retrieveAttr("name", oAuth2User));
        customUserDetails.setEmail(retrieveAttr("email", oAuth2User));
        customUserDetails.setProvider(Provider.GITHUB);
        customUserDetails.setAttributes(oAuth2User.getAttributes());
        customUserDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return customUserDetails;
    }

    @Override
    public boolean accepts(OAuth2UserRequest userRequest) {
        return Provider.GITHUB.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }

    private String retrieveAttr(String attr, OAuth2User oAuth2User) {
        Object attribute = oAuth2User.getAttributes().get(attr);
        return attribute == null ? "" : attribute.toString();
    }
}
