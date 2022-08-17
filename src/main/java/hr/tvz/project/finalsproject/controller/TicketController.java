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

    @GetMapping(params = "category_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByCategoryId(@RequestParam final Long category_id){
        return ticketService.findByCategoryId(category_id);
    }

    @GetMapping(params = "assignee_id")
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public List<TicketDTO> findTicketByAssigneeId(@RequestParam final Long assignee_id){
        return ticketService.findByAssigneeId(assignee_id);
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

    @PostMapping(value = "/addTicket", params = {"assignee_id", "tester_id", "category_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> save(@RequestBody Ticket ticket, @RequestParam Long assignee_id,
                                            @RequestParam Long tester_id, @RequestParam Long category_id){
        Optional<User> userOptional = userService.findByIdRaw(assignee_id);
        Optional<User> testerOptional = userService.findByIdRaw(tester_id);
        Optional<Category> categoryOptional = categoryService.findByIdRaw(category_id);
        if (userOptional.isPresent() && testerOptional.isPresent() && categoryOptional.isPresent()){
            User a = userOptional.get();
            User t = testerOptional.get();
            Category c = categoryOptional.get();
            TicketDTO _ticket = ticketService.save(ticket, a, t, c);
            return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/updateTicket", params = {"ticket_id", "assignee_id", "tester_id", "category_id"})
    @Secured({"ROLE_ADMIN", "ROLE_TEAM_MODERATOR", "ROLE_USER"})
    public ResponseEntity<TicketDTO> update(@RequestBody Ticket ticket, @RequestParam Long ticket_id,
                                                @RequestParam Long assignee_id, @RequestParam Long tester_id,
                                                @RequestParam Long category_id) {
        Optional<Ticket> ticketOptional = ticketService.findByIdRaw(ticket_id);
        Optional<User> userOptional = userService.findByIdRaw(assignee_id);
        Optional<User> testerOptional = userService.findByIdRaw(tester_id);
        Optional<Category> categoryOptional = categoryService.findByIdRaw(category_id);
        if (ticketOptional.isPresent() && userOptional.isPresent()
            && testerOptional.isPresent() && categoryOptional.isPresent()) {
            User a = userOptional.get();
            User t = testerOptional.get();
            Category c = categoryOptional.get();
            return new ResponseEntity<>(ConvertorsDTO.mapTicketToDTO(ticketService.update(ticket,a, t, c)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void deleteById(@PathVariable Long id){
        ticketService.delete(id);
    }
}
