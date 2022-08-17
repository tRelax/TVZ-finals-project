package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.service.TeamService;
import hr.tvz.project.finalsproject.service.TicketService;
import hr.tvz.project.finalsproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public List<UserDTO> findAllUser(){
        return userService.findAll();
    }

    @GetMapping(params = "name")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<UserDTO> findUserByName(@RequestParam final String name){
        return userService.findByName(name);
    }

    @GetMapping(params = "team_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<UserDTO> findUsersByTeamId(@RequestParam final Long team_id){
        return userService.findByTeamId(team_id);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> findUserById(@PathVariable final Long id){
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @GetMapping(params="assignee_ticket_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> findAssigneeByTicketId(@RequestParam final Long assignee_ticket_id){
        return userService.findAssigneeByTicketId(assignee_ticket_id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @GetMapping(params="tester_ticket_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> findTesterByTicketId(@RequestParam final Long tester_ticket_id){
        return userService.findTesterByTicketId(tester_ticket_id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping(value = "/addUser", params = "password")
    public ResponseEntity<UserDTO> save(@RequestBody final User user, @RequestParam final String password){
        try {
            UserDTO _user = userService.save(user, password);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.findByIdRaw(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(ConvertorsDTO.mapUserToDTO(userService.update(user)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }

}
