package hr.tvz.project.finalsproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teams")
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
