package hr.tvz.project.finalsproject.convertorsDTO;

import hr.tvz.project.finalsproject.DTO.CategoryDTO;
import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;

import java.util.List;
import java.util.Objects;

public class ConvertorsDTO {

    public static UserDTO mapUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .teamList(mapListTeamToDTO(user.getTeamList()))
                .ticketList(mapListTicketToDTO(user.getTicketList()))
                .ticketListTester(mapListTicketToDTO(user.getTicketListTester()))
                .build();
    }

    public static UserDTO mapUserToSimpleDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public static TeamDTO mapTeamToDTO(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .membersList(mapListUserToDTO(team.getMembersList()))
                .ticketList(mapListTicketToDTO(team.getTicketList()))
                .build();
    }

    public static TeamDTO mapTeamToSimpleDTO(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public static CategoryDTO mapCategoryToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .ticketList(mapListTicketToDTO(Objects.requireNonNull(category.getTicketList())))
                .build();
    }

    public static CategoryDTO mapCategoryToSimpleDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static TicketDTO mapTicketToDTO(Ticket ticket) {
       return TicketDTO.builder()
               .id(ticket.getId())
               .name(ticket.getName())
               .description(ticket.getDescription())
               .start_date(ticket.getStart_date())
               .due_date(ticket.getDue_date())
               .progress(ticket.getProgress())
               .priority(ticket.getPriority())
               .category(mapCategoryToSimpleDTO(ticket.getCategory()))
               .team(mapTeamToSimpleDTO(ticket.getTeam()))
               .user(mapUserToSimpleDTO(ticket.getUser()))
               .user_tester(mapUserToSimpleDTO(ticket.getUser_tester()))
               .build();
    }


    public static List<UserDTO> mapListUserToDTO(List<User> list){
        return list
                .stream()
                .map(c -> UserDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .surname(c.getSurname())
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

    public static List<TicketDTO> mapListTicketToDTO(List<Ticket> list){
        return list
                .stream()
                .map(c -> TicketDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .description(c.getDescription())
                        .start_date(c.getStart_date())
                        .due_date(c.getDue_date())
                        .progress(c.getProgress())
                        .priority(c.getPriority())
                        .build()
                ).toList();
    }
}
