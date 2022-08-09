package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.CategoryDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.service.CategoryService;
import hr.tvz.project.finalsproject.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {
    private final CategoryService categoryService;
    private final TicketService ticketService;

    public CategoryController(CategoryService categoryService, TicketService ticketService) {
        this.categoryService = categoryService;
        this.ticketService = ticketService;
    }

    @GetMapping()
    public List<CategoryDTO> findAllCategories(){
        return categoryService.findAll();
    }

    @GetMapping(params = "name")
    public List<CategoryDTO> findCategoryByName(@RequestParam final String name){
        return categoryService.findByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable final Long id){
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @GetMapping(params = "ticket_id")
    public ResponseEntity<CategoryDTO> findCategoryByTicketId(@RequestParam final Long ticket_id){
        return categoryService.findByTicketId(ticket_id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> save(@RequestBody final Category category){
        try {
            CategoryDTO _category = categoryService.save(category);
            return new ResponseEntity<>(_category, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody Category category) {
        Optional<CategoryDTO> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            return new ResponseEntity<>(ConvertorsDTO.mapCategoryToDTO(categoryService.update(category)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "ticket_id_add")
    public ResponseEntity<CategoryDTO> updateTicketListAdd(@RequestParam Long ticket_id_add, @RequestBody Long category_id) {
        Optional<Category> categoryOptional = categoryService.findByIdRaw(category_id);
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id_add);
        if (categoryOptional.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(categoryService.updateTicketListAdd(categoryOptional.get(), ticketOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "ticket_id_remove")
    public ResponseEntity<CategoryDTO> updateTicketListRemove(@RequestParam Long ticket_id_remove, @RequestBody Long category_id) {
        Optional<Category> categoryOptional = categoryService.findByIdRaw(category_id);
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id_remove);
        if (categoryOptional.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(categoryService.updateTicketListRemove(categoryOptional.get(), ticketOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        categoryService.delete(id);
    }

}
