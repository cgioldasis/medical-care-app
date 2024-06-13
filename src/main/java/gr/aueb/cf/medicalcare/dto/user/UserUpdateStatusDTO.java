package gr.aueb.cf.medicalcare.dto.user;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateStatusDTO extends BaseDTO {

    private String Status;

    public UserUpdateStatusDTO(@NotNull Long id, String status) {
        super(id);
        Status = status;
    }
}
