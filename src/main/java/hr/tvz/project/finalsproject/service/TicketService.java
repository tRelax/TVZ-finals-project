package hr.tvz.project.finalsproject.service;


import hr.tvz.project.finalsproject.DTO.TicketDTO;
import hr.tvz.project.finalsproject.entity.Category;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<TicketDTO> findAll();
    List<TicketDTO> findByName(String name);
    List<TicketDTO> findByCategoryName(String name);
    List<TicketDTO> findByCategoryId(Long id);
    List<TicketDTO> findByAssigneeName(String name);
    List<TicketDTO> findByAssigneeId(Long id);
    List<TicketDTO> findByTesterName(String name);
    Optional<TicketDTO> findById(Long id);
    Optional<Ticket> findByIdRaw(Long id);
    TicketDTO save(Ticket ticket, User assignee, User tester, Category category);
    Ticket update(Ticket ticket, User assignee, User tester, Category category);
    void delete(Long id);
}
