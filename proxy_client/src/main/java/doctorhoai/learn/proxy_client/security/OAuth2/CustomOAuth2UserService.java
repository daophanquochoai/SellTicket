package doctorhoai.learn.proxy_client.security.OAuth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.CustomerDto;
import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
import doctorhoai.learn.proxy_client.business.user.service.CustomerFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final CustomerFeign customerFeign;
    private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractor = oAuth2UserInfoExtractors.stream().filter(i -> i.accepts(userRequest)).findFirst();
        if(oAuth2UserInfoExtractor.isEmpty()){
            throw new InternalAuthenticationServiceException("Phần mềm chưa hỗ trợ");
        }
        CustomUserDetails customUserDetails = oAuth2UserInfoExtractor.get().extractUserInfo(oAuth2User);
        CustomerRequest customerRequest = CustomerRequest.builder()
                .email(customUserDetails.getEmail())
                .userName(customUserDetails.getUsername())
                .name(customUserDetails.getName())
                .provider(customUserDetails.getProvider())
                .build();
        ResponseEntity<Response> response = customerFeign.addCustomerSocial(customerRequest);
        if( response.getStatusCode() != HttpStatusCode.valueOf(201) ){
            throw new OAuth2AuthenticationException("OAuth Exception");
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        CustomerDto customerDto = objectMapper.convertValue(response.getBody().getData(), CustomerDto.class);
        customUserDetails.setId(customerDto.getId());
        return customUserDetails;
    }
}
