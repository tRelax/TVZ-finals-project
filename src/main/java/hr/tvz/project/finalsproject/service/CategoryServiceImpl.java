package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.CategoryDTO;
import hr.tvz.project.finalsproject.DTO.TeamDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(ConvertorsDTO::mapCategoryToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapCategoryToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id).map(ConvertorsDTO::mapCategoryToDTO);
    }

    @Override
    public Optional<Category> findByIdRaw(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<CategoryDTO> findByTicketId(Long id) {
        return categoryRepository.findByTicketListId(id).map(ConvertorsDTO::mapCategoryToDTO);
    }

    @Override
    public CategoryDTO save(Category category) {
        List<CategoryDTO> listOfAllCategories = findAll();
        if(!listOfAllCategories.isEmpty()){
            CategoryDTO lCategory = listOfAllCategories.stream().max(Comparator.comparing(CategoryDTO::getId)).get();
            if (category.getId() <= lCategory.getId())
                category.setId(lCategory.getId() + 1);
        }
        return ConvertorsDTO.mapCategoryToDTO(categoryRepository.save(category));
    }

    @Override
    public Category update(Category category) {
        Optional<Category> tempCategory = findByIdRaw(category.getId());
        if(tempCategory.isPresent()){
            category.setTicketList(tempCategory.get().getTicketList());
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
