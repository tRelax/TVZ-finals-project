package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByNameContainingIgnoreCase(String name);
    List<Ticket> findByCategoryNameContainingIgnoreCase(String name);
    List<Ticket> findByCategoryId(Long id);
    List<Ticket> findByAssigneeNameContainingIgnoreCase(String name);
    List<Ticket> findByAssigneeId(Long id);
    List<Ticket> findByTesterNameContainingIgnoreCase(String name);
}
