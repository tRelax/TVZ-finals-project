package hr.tvz.project.finalsproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTO {
    private Long id;
    private String name;
    private String description;
    private List<UserDTO> membersList;
    private List<TicketDTO> ticketList;
}
