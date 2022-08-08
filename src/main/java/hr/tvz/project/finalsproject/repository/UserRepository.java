package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByTeamListId(Long id);
    Optional<User> findByTicketListId(Long id);
    Optional<User> findByTicketListTesterId(Long id);
}
