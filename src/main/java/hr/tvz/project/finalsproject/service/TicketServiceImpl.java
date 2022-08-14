package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    public List<TicketDTO> findByName(String name) {
        return ticketRepository.findByNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> findByCategoryName(String name) {
        return ticketRepository.findByCategoryNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> findByCategoryId(Long id) {
        return ticketRepository.findByCategoryId(id).stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> findByAssigneeName(String name) {
        return ticketRepository.findByAssigneeNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> findByAssigneeId(Long id) {
        return ticketRepository.findByAssigneeId(id).stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> findByTesterName(String name) {
        return ticketRepository.findByTesterNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapTicketToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TicketDTO> findById(Long id) {
        return ticketRepository.findById(id).map(ConvertorsDTO::mapTicketToDTO);
    }

    @Override
    public Optional<Ticket> findByIdRaw(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public TicketDTO save(Ticket ticket) {
        List<TicketDTO> listOfAllTickets = findAll();
        if(!listOfAllTickets.isEmpty()){
            TicketDTO tempTicket = listOfAllTickets.stream().max(Comparator.comparing(TicketDTO::getId)).get();
            if (ticket.getId() <= tempTicket.getId())
                ticket.setId(tempTicket.getId() + 1);
        }
        return ConvertorsDTO.mapTicketToDTO(ticketRepository.save(ticket));
    }

    @Override
    public Ticket update(Ticket ticket) {
        Optional<Ticket> tempTicket = findByIdRaw(ticket.getId());
        if(tempTicket.isPresent()){
            ticket.setCategory(tempTicket.get().getCategory());
            ticket.setAssignee(tempTicket.get().getAssignee());
            ticket.setTester(tempTicket.get().getTester());
        }
        return ticketRepository.save(ticket);
    }

    @Override
    public TicketDTO updateAssignee(Ticket ticket, User user) {
        Optional<Ticket> tempTicket = findByIdRaw(ticket.getId());
        if(tempTicket.isPresent()){
            ticket.setAssignee(user);
        }
        return ConvertorsDTO.mapTicketToDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketDTO updateTester(Ticket ticket, User user) {
        Optional<Ticket> tempTicket = findByIdRaw(ticket.getId());
        if(tempTicket.isPresent()){
            ticket.setTester(user);
        }
        return ConvertorsDTO.mapTicketToDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketDTO updateCategory(Ticket ticket, Category category) {
        Optional<Ticket> tempTicket = findByIdRaw(ticket.getId());
        if(tempTicket.isPresent()){
            ticket.setCategory(category);
        }
        return ConvertorsDTO.mapTicketToDTO(ticketRepository.save(ticket));
    }

    @Override
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
