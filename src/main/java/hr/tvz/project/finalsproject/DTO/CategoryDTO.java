package hr.tvz.project.finalsproject.DTO;

import hr.tvz.project.finalsproject.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
//    private List<Ticket> ticketList;
}
