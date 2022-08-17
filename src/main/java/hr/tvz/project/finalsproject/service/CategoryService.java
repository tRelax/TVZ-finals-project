package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.CategoryDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> findAll();
    List<CategoryDTO> findByName(String name);
    Optional<CategoryDTO> findById(Long id);
    Optional<Category> findByIdRaw(Long id);
    Optional<CategoryDTO> findByTicketId(Long id);
    CategoryDTO save(Category category);
    Category update(Category category);
    void delete(Long id);
}
