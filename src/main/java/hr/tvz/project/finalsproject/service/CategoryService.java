package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.CategoryDTO;
import hr.tvz.project.finalsproject.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> findAll();
    List<CategoryDTO> findByName(String name);
    Optional<CategoryDTO> findById(Long id);
    CategoryDTO save(Category category, boolean save);

    void delete(Long id);
}
