package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.repository.TeamRepository;
import hr.tvz.project.finalsproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamDTO> findAll() {
        return teamRepository.findAll().stream().map(ConvertorsDTO::mapTeamToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TeamDTO> findById(Long id) {
        return teamRepository.findById(id).map(ConvertorsDTO::mapTeamToDTO);
    }

    @Override
    public TeamDTO save(Team team) {
        return ConvertorsDTO.mapTeamToDTO(teamRepository.save(team));
    }

}
