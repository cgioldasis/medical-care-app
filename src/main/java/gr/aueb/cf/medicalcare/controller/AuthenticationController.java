package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.AuthenticationRequest;
import gr.aueb.cf.medicalcare.dto.AuthenticationResponse;
import gr.aueb.cf.medicalcare.security.CustomUserDetails;
import gr.aueb.cf.medicalcare.security.CustomUserDetailsService;
import gr.aueb.cf.medicalcare.security.JwtUtil;
import gr.aueb.cf.medicalcare.service.exception.NotActiveUserException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @SneakyThrows
    @PostMapping("/api/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception, NotActiveUserException {
        // Επαληθεύει τον χρήστη
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Δημιουργεί JWT token
        final String jwt = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwt);

        return new AuthenticationResponse(jwt);
    }
}