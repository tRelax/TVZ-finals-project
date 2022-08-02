package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
