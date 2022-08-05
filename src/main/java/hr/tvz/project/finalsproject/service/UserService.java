package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> findAll();
    List<UserDTO> findByName(String name);
    Optional<UserDTO> findById(Long id);
    UserDTO save(User user, boolean save);
    void delete(Long id);
}
