package hr.tvz.project.finalsproject.controller;

import hr.tvz.project.finalsproject.DTO.CategoryDTO;
import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.service.CategoryService;
import hr.tvz.project.finalsproject.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping()
    public List<TicketDTO> findAllTickets(){
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> findCategoryById(@PathVariable final Long id){
        return ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @PostMapping()
    public TicketDTO save(@RequestBody final Ticket ticket){
        return ticketService.save(ticket);
    }
}
