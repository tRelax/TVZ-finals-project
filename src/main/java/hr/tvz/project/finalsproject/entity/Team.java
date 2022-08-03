package hr.tvz.project.finalsproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_team")
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "users_has_teams",
        joinColumns = @JoinColumn(name = "teams_id"),
        inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> membersList;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Ticket> ticketList;
}
