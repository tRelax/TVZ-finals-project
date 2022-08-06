package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    public List<TeamDTO> findByName(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapTeamToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TeamDTO> findByUserId(Long id) {
        return teamRepository.findByMembersListId(id).stream().map(ConvertorsDTO::mapTeamToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TeamDTO> findById(Long id) {
        return teamRepository.findById(id).map(ConvertorsDTO::mapTeamToDTO);
    }

    @Override
    public Optional<TeamDTO> findByTicketId(Long id) {
        return teamRepository.findByTicketListId(id).map(ConvertorsDTO::mapTeamToDTO);
    }

    @Override
    public TeamDTO save(Team team, boolean save) {
        if(save){
            List<TeamDTO> listOfAllTeams = findAll();
            if(!listOfAllTeams.isEmpty()){
                TeamDTO tempTeam = listOfAllTeams.stream().max(Comparator.comparing(TeamDTO::getId)).get();
                if (team.getId() <= tempTeam.getId())
                    team.setId(tempTeam.getId() + 1);
            }
        }
        return ConvertorsDTO.mapTeamToDTO(teamRepository.save(team));
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

}
