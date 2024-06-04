package gr.aueb.cf.medicalcare.dto;

import lombok.Getter;

@Getter
public class AuthenticationResponse {

    // Jwt token
    private final String jwt;

    // Constructor
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

}
