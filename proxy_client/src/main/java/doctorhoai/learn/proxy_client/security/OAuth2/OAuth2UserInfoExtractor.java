package doctorhoai.learn.proxy_client.security.OAuth2;

import doctorhoai.learn.proxy_client.business.user.model.request.Provider;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfoExtractor {
    CustomUserDetails extractUserInfo(OAuth2User oauth2User);
    boolean accepts(OAuth2UserRequest oAuth2UserRequest);
}
