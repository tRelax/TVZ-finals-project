package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDTO> findAll();
    List<TeamDTO> findByName(String name);
    List<TeamDTO> findByUserId(Long id);
    Optional<TeamDTO> findById(Long id);
    Optional<TeamDTO> findByTicketId(Long id);
    TeamDTO save(Team team, boolean save);
    void delete(Long id);
}
