package gr.aueb.cf.medicalcare.dto.user;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import gr.aueb.cf.medicalcare.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserStatusDTO extends BaseDTO {

    private String username;
    private String email;
    private String status;

    public UserStatusDTO(@NotNull Long id, String username, String email, String status) {
        super(id);
        this.username = username;
        this.email = email;
        this.status = status;
    }
}
