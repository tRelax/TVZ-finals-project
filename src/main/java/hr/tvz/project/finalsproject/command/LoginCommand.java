package hr.tvz.project.finalsproject.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginCommand {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
