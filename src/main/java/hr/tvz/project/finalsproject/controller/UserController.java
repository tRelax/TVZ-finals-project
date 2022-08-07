package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.User;
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

    public UserController(UserService userService) {
        this.userService = userService;
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
    public List<UserDTO> findTicketByTeamId(@RequestParam final Long team_id){
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }
}
