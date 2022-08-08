package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.service.TeamService;
import hr.tvz.project.finalsproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    private final TeamService teamService;

    public UserController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @GetMapping()
    public List<UserDTO> findAllUser(){
        return userService.findAll();
    }

    @GetMapping(params = "name")
    public List<UserDTO> findUserByName(@RequestParam final String name){
        return userService.findByName(name);
    }

    @GetMapping(params = "team_id")
    public List<UserDTO> findUsersByTeamId(@RequestParam final Long team_id){
        return userService.findByTeamId(team_id);
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<UserDTO> findTesterByTicketId(@RequestParam final Long tester_ticket_id){
        return userService.findTesterByTicketId(tester_ticket_id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping()
    public ResponseEntity<UserDTO> save(@RequestBody final User user){
        try {
            UserDTO _user = userService.save(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.findByIdRaw(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(ConvertorsDTO.mapUserToDTO(userService.update(user)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "add_id")
    public ResponseEntity<UserDTO> updateUserTeams(@PathVariable Long add_id, @RequestBody Long team_id) {
        Optional<User> userOptional = userService.findByIdRaw(add_id);
        Optional<Team> teamOptional = teamService.findByIdRaw(team_id);
        if (userOptional.isPresent() && teamOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateUserTeams(userOptional.get(), teamOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "remove_id")
    public ResponseEntity<UserDTO> updateUserTeamsRemove(@RequestParam Long remove_id, @RequestBody Long team_id) {
        Optional<User> userOptional = userService.findByIdRaw(remove_id);
        Optional<Team> teamOptional = teamService.findByIdRaw(team_id);
        if (userOptional.isPresent() && teamOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateUserTeamsRemove(userOptional.get(), teamOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }

}
