package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
