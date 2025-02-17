package doctorhoai.learn.proxy_client.business.auth.service.impl;

import doctorhoai.learn.proxy_client.business.auth.model.request.AuthenticationRequest;
import doctorhoai.learn.proxy_client.business.auth.model.response.AuthenticationResponse;
import doctorhoai.learn.proxy_client.business.auth.service.AuthenticationService;
import doctorhoai.learn.proxy_client.exception.InternalServer;
import doctorhoai.learn.proxy_client.jwt.service.JwtService;
import doctorhoai.learn.proxy_client.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        log.info("** AuthenticationResponse, authenticate user service **");

        //TODO: chua ma hoa mat khau
        try{
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()
                    )
            );
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        String token = "";
        try{
            token = this.jwtService.generateToken(userDetailsService.loadUserByUsername(authenticationRequest.getUsername()));
            tokenService.saveToken(token, authenticationRequest.getUsername());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new AuthenticationResponse(
                token
        );
    }

    @Override
    public void logout(String token) {
        log.info("** Response, logout service **");
        try{
            this.tokenService.deleteToken(token);
        }catch (Exception e) {
            throw new InternalServer(e.getMessage());
        }
    }


}
