package hr.tvz.project.finalsproject.convertorsDTO;

import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.User;

import java.util.List;

public class ConvertorsDTO {

    public static UserDTO mapUserToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), mapListTeamToDTO(user.getTeamList()));
    }

    public static TeamDTO mapTeamToDTO(Team team) {
        return new TeamDTO(team.getId(), team.getName(), team.getDescription(), mapListUserToDTO(team.getMembersList()));
    }

    public static List<UserDTO> mapListUserToDTO(List<User> list){
        return list
                .stream()
                .map(c -> UserDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .build()
                ).toList();
    }
    public static List<TeamDTO> mapListTeamToDTO(List<Team> list){
        return list
                .stream()
                .map(c -> TeamDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .description(c.getDescription())
                        .build()
                ).toList();
    }
}
