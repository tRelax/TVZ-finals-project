package hr.tvz.project.finalsproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long id;

    private String name;
    private String description;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date start_date;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date due_date;
    private Integer progress;
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "teams_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "users_tester_id")
    private User user_tester;
}
