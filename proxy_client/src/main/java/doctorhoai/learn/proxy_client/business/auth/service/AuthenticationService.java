package doctorhoai.learn.proxy_client.business.auth.service;

import doctorhoai.learn.proxy_client.business.auth.model.request.AuthenticationRequest;
import doctorhoai.learn.proxy_client.business.auth.model.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate( final AuthenticationRequest authenticationRequest );
    void logout(final String token);
}
