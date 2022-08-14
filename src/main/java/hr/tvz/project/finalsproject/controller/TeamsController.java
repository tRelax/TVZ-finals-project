package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.service.TeamService;
import hr.tvz.project.finalsproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "http://localhost:4200")
public class TeamsController {
    private final TeamService teamService;
    private final UserService userService;

    public TeamsController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TeamDTO> findAllTeams(){
        return teamService.findAll();
    }

    @GetMapping(params = "name")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TeamDTO> findTeamByName(@RequestParam final String name){
        return teamService.findByName(name);
    }

    @GetMapping(params = "user_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TeamDTO> findTeamByUserId(@RequestParam final Long user_id){
        return teamService.findByUserId(user_id);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TeamDTO> findTeamById(@PathVariable final Long id){
        return teamService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<TeamDTO> save(@RequestBody final Team team){
        try {
            TeamDTO _team = teamService.save(team);
            return new ResponseEntity<>(_team, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR"})
    public ResponseEntity<TeamDTO> update(@PathVariable Long id, @RequestBody Team team) {
        Optional<Team> teamOptional = teamService.findByIdRaw(id);
        if (teamOptional.isPresent()) {
            return new ResponseEntity<>(ConvertorsDTO.mapTeamToDTO(teamService.update(team)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "add_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR"})
    public ResponseEntity<TeamDTO> updateUserTeamsAdd(@RequestParam Long add_id, @RequestBody Long user_id) {
        Optional<Team> teamOptional = teamService.findByIdRaw(add_id);
        Optional<User> userOptional = userService.findByIdRaw(user_id);
        if (teamOptional.isPresent() && userOptional.isPresent()) {
            return new ResponseEntity<>(teamService.updateTeamMembersAdd(teamOptional.get(), userOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "remove_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR"})
    public ResponseEntity<TeamDTO> updateUserTeamsRemove(@RequestParam Long remove_id, @RequestBody Long user_id) {
        Optional<Team> teamOptional = teamService.findByIdRaw(remove_id);
        Optional<User> userOptional = userService.findByIdRaw(user_id);
        if (teamOptional.isPresent() && userOptional.isPresent()) {
            return new ResponseEntity<>(teamService.updateTeamMembersRemove(teamOptional.get(), userOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void deleteById(@PathVariable Long id){
        teamService.delete(id);
    }

}
