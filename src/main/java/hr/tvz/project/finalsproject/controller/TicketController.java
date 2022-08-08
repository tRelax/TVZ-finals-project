package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping()
    public List<TicketDTO> findAllTickets(){
        return ticketService.findAll();
    }

    @GetMapping(params = "name")
    public List<TicketDTO> findTicketByName(@RequestParam final String name){
        return ticketService.findByName(name);
    }

    @GetMapping(params = "category")
    public List<TicketDTO> findTicketByCategoryName(@RequestParam final String category){
        return ticketService.findByCategoryName(category);
    }

    @GetMapping(params = "category_id")
    public List<TicketDTO> findTicketByCategoryId(@RequestParam final Long category_id){
        return ticketService.findByCategoryId(category_id);
    }

    @GetMapping(params = "assignee")
    public List<TicketDTO> findTicketByAssignee(@RequestParam final String assignee){
        return ticketService.findByAssigneeName(assignee);
    }

    @GetMapping(params = "assignee_id")
    public List<TicketDTO> findTicketByAssigneeId(@RequestParam final Long assignee_id){
        return ticketService.findByAssigneeId(assignee_id);
    }

    @GetMapping(params = "tester")
    public List<TicketDTO> findTicketByTester(@RequestParam final String tester){
        return ticketService.findByTesterName(tester);
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<TicketDTO> save(@RequestBody final Ticket ticket){
        try {
            TicketDTO _ticket = ticketService.save(ticket, true);
            return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        Optional<TicketDTO> ticketOptional = ticketService.findById(id);
        if (ticketOptional.isPresent()) {
            return new ResponseEntity<>(ticketService.save(ticket, false), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        ticketService.delete(id);
    }
}
