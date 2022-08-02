package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
