package hr.tvz.project.finalsproject.service;


import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<TicketDTO> findAll();
    Optional<TicketDTO> findById(Long id);
    TicketDTO save(Ticket ticket);
}
