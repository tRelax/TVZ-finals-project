package hr.tvz.project.finalsproject.DTO;

import hr.tvz.project.finalsproject.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private List<TeamDTO> teamList;
}
