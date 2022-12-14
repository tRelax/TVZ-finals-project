package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> findAll();
    List<UserDTO> findByTeamId(Long id);
    Optional<UserDTO> findById(Long id);
    Optional<User> findByIdRaw(Long id);
    Optional<UserDTO> findAssigneeByTicketId(Long id);
    Optional<UserDTO> findTesterByTicketId(Long id);
    UserDTO save(User user, String password);
    User update(User user);
    void delete(Long id);
}
