package hr.tvz.project.finalsproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;

    private String name;
    private String description;

    @Nullable
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Ticket> ticketList;
}
