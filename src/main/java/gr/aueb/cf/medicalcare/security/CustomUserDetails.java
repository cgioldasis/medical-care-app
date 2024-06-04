package gr.aueb.cf.medicalcare.security;

import gr.aueb.cf.medicalcare.model.Status;
import org.springframework.security.core.userdetails.UserDetails;


public interface CustomUserDetails extends UserDetails {

    Status getStatus();
}
