package gr.aueb.cf.medicalcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    // Getters και setters
    private String username;
    private String password;

    // Default constructor για χρήση από Spring
    public AuthenticationRequest() {}

    // Constructor για εύκολη δημιουργία αντικειμένων
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}