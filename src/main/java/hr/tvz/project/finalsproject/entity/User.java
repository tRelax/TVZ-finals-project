package hr.tvz.project.finalsproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private String password;

    @ManyToMany(mappedBy = "membersList")
    private List<Team> teamList;

    @OneToMany(mappedBy = "assignee", fetch = FetchType.EAGER)
    private List<Ticket> ticketList;

    @OneToMany(mappedBy = "tester", fetch = FetchType.EAGER)
    private List<Ticket> ticketListTester;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_has_authority",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "authorities_id")}
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
}
