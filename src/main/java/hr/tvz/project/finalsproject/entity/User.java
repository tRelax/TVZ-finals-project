package hr.tvz.project.finalsproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Long id;

    private String username;
    private String name;
    private String surname;
    private String password;

    @ManyToMany(mappedBy = "membersList")
    private List<Team> teamList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Ticket> ticketList;

    @OneToMany(mappedBy = "user_tester", fetch = FetchType.EAGER)
    private List<Ticket> ticketListTester;
}
