package doctorhoai.learn.proxy_client.business.auth.controller;

import doctorhoai.learn.proxy_client.business.auth.model.request.AuthenticationRequest;
import doctorhoai.learn.proxy_client.business.auth.model.request.Response;
import doctorhoai.learn.proxy_client.business.auth.model.response.AuthenticationResponse;
import doctorhoai.learn.proxy_client.business.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody
        @NotNull(message = "*Data must not be null!*")
        @Valid
        final AuthenticationRequest authenticationRequest
    ){
        log.info("** Authentication controller, proceed with the request **");
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity<Response> logout(
            HttpServletRequest request
    ){
        log.info("** Authentication controller, proceed with the request logout **");
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        authenticationService.logout(token);
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .message("* Logout success! *")
                        .build()
        );
    }
}
