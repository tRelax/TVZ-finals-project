package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDTO> findAll();
    List<TeamDTO> findByName(String name);
    List<TeamDTO> findByUserId(Long id);
    Optional<TeamDTO> findById(Long id);
    Optional<Team> findByIdRaw(Long id);
    TeamDTO save(Team team);
    Team update(Team team);
    TeamDTO updateTeamMembersAdd(Team team, User user);
    TeamDTO updateTeamMembersRemove(Team team, User user);
    void delete(Long id);
}
