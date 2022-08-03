package hr.tvz.project.finalsproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    private Long id;

    private String name;
    private String description;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date start_date;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date due_date;
    private Integer progress;
    private Integer priority;
    private CategoryDTO category;
    private TeamDTO team;
    private UserDTO user;
    private UserDTO user_tester;
}
