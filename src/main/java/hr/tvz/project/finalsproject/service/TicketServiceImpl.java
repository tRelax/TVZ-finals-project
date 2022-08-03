package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<TicketDTO> findAll() {
        return ticketRepository.findAll().stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TicketDTO> findById(Long id) {
        return ticketRepository.findById(id).map(ConvertorsDTO::mapTicketToDTO);
    }

    @Override
    public TicketDTO save(Ticket ticket) {
        return ConvertorsDTO.mapTicketToDTO(ticketRepository.save(ticket));
    }
}
