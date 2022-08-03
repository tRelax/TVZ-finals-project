package hr.tvz.project.finalsproject.repository;

import hr.tvz.project.finalsproject.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
