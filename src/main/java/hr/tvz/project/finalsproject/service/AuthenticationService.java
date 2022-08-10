package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.LoginDTO;
import hr.tvz.project.finalsproject.command.LoginCommand;

import java.util.Optional;

public interface AuthenticationService {
    Optional<LoginDTO> login(LoginCommand command);

}
