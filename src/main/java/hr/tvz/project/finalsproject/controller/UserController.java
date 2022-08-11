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
    private final TeamService teamService;
    private final TicketService ticketService;

    public UserController(UserService userService, TeamService teamService, TicketService ticketService) {
        this.userService = userService;
        this.teamService = teamService;
        this.ticketService = ticketService;
    }

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
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

    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> save(@RequestBody final User user){
        try {
            UserDTO _user = userService.save(user);
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

    @PatchMapping(params = "add_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> updateUserTeams(@RequestParam Long add_id, @RequestBody Long team_id) {
        Optional<User> userOptional = userService.findByIdRaw(add_id);
        Optional<Team> teamOptional = teamService.findByIdRaw(team_id);
        if (userOptional.isPresent() && teamOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateUserTeams(userOptional.get(), teamOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "remove_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> updateUserTeamsRemove(@RequestParam Long remove_id, @RequestBody Long team_id) {
        Optional<User> userOptional = userService.findByIdRaw(remove_id);
        Optional<Team> teamOptional = teamService.findByIdRaw(team_id);
        if (userOptional.isPresent() && teamOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateUserTeamsRemove(userOptional.get(), teamOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = {"assignee_add_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> updateAssigneeTicketListAdd(@RequestParam Long assignee_add_id, @RequestBody Long ticket_id) {
        Optional<User> userOptionalAdd = userService.findByIdRaw(assignee_add_id);
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        if (userOptionalAdd.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateAssigneeTicketListAdd(userOptionalAdd.get(), ticketOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = {"assignee_remove_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> updateAssigneeTicketListRemove(@RequestParam Long assignee_remove_id, @RequestBody Long ticket_id) {
        Optional<User> userOptionalRemove = userService.findByIdRaw(assignee_remove_id);
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        if (userOptionalRemove.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateAssigneeTicketListRemove(userOptionalRemove.get(), ticketOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = {"tester_add_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> updateTesterTicketListAdd(@RequestParam Long tester_add_id, @RequestBody Long ticket_id) {
        Optional<User> userOptionalAdd = userService.findByIdRaw(tester_add_id);
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        if (userOptionalAdd.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateTesterTicketListAdd(userOptionalAdd.get(), ticketOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = {"tester_remove_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<UserDTO> updateTesterTicketListRemove(@RequestParam Long tester_remove_id, @RequestBody Long ticket_id) {
        Optional<User> userOptionalRemove = userService.findByIdRaw(tester_remove_id);
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        if (userOptionalRemove.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(userService.updateTesterTicketListRemove(userOptionalRemove.get(), ticketOptional.get()), HttpStatus.OK);
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
