package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamsController {
    private final TeamService teamService;

    public TeamsController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping()
    public List<TeamDTO> findAllTeams(){
        return teamService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findUserById(@PathVariable final Long id){
        return teamService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping()
    public TeamDTO save(@RequestBody final Team team){
        return teamService.save(team);
    }
}
