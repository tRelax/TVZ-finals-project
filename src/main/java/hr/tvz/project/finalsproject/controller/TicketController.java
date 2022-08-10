package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.service.CategoryService;
import hr.tvz.project.finalsproject.service.TicketService;
import hr.tvz.project.finalsproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final CategoryService categoryService;

    public TicketController(TicketService ticketService, UserService userService, CategoryService categoryService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findAllTickets(){
        return ticketService.findAll();
    }

    @GetMapping(params = "name")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByName(@RequestParam final String name){
        return ticketService.findByName(name);
    }

    @GetMapping(params = "category")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByCategoryName(@RequestParam final String category){
        return ticketService.findByCategoryName(category);
    }

    @GetMapping(params = "category_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByCategoryId(@RequestParam final Long category_id){
        return ticketService.findByCategoryId(category_id);
    }

    @GetMapping(params = "assignee")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByAssignee(@RequestParam final String assignee){
        return ticketService.findByAssigneeName(assignee);
    }

    @GetMapping(params = "assignee_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByAssigneeId(@RequestParam final Long assignee_id){
        return ticketService.findByAssigneeId(assignee_id);
    }

    @GetMapping(params = "tester")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByTester(@RequestParam final String tester){
        return ticketService.findByTesterName(tester);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> findTicketById(@PathVariable final Long id){
        return ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> save(@RequestBody final Ticket ticket){
        try {
            TicketDTO _ticket = ticketService.save(ticket);
            return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(id);
        if (ticketOptional.isPresent()) {
            return new ResponseEntity<>(ConvertorsDTO.mapTicketToDTO(ticketService.update(ticket)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = {"assignee_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> updateTicketAssignee(@RequestParam Long assignee_id, @RequestBody Long ticket_id) {
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        Optional<User> userOptional = userService.findByIdRaw(assignee_id);
        if (userOptional.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(ticketService.updateAssignee(ticketOptional.get(), userOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = {"tester_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> updateTicketTester(@RequestParam Long tester_id, @RequestBody Long ticket_id) {
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        Optional<User> userOptional = userService.findByIdRaw(tester_id);
        if (userOptional.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(ticketService.updateTester(ticketOptional.get(), userOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(params = "category_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> updateTicketCategory(@RequestParam Long category_id, @RequestBody Long ticket_id) {
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        Optional<Category> categoryOptional = categoryService.findByIdRaw(category_id);
        if (categoryOptional.isPresent() && ticketOptional.isPresent()) {
            return new ResponseEntity<>(ticketService.updateCategory(ticketOptional.get(), categoryOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public void deleteById(@PathVariable Long id){
        ticketService.delete(id);
    }
}
