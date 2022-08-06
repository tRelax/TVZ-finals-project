package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameContainingIgnoreCase(String name);
    List<Team> findByMembersListId(Long id);
    Optional<Team> findByTicketListId(Long id);
}
