package recipes.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Slf4j
public class UserRegisterRequest {
    @NotEmpty(message = "Email field cannot be empty.")
    @Pattern(regexp = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]+", message = "Email must be valid and needs to contain both '@' and '.' symbols.")
    private String email;
    @NotEmpty(message = "Password field cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @NotBlank
    private String password;
}
