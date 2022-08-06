package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingIgnoreCase(String name);
    Optional<Category> findByTicketListId(Long id);
}
