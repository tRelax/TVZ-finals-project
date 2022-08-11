import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/category-all/category';
import { CategoryService } from 'src/app/category-all/category.service';
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Ticket } from '../ticket';
import { TicketService } from '../ticket.service';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['../../../styles.css']
})
export class TicketComponent implements OnInit {

  tickets?: Ticket[];
  assigneeMap = new Map<Number, User>();
  testerMap = new Map<Number, User>();
  categoryMap = new Map<Number, Category>();
  assignee?: User;
  tester?: User;
  isUserAdmin: boolean;

  constructor(
    private ticketService: TicketService,
    private userService: UserService,
    private categoryService: CategoryService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
    this.getTickets();
  }

  getTickets(): void {
    this.ticketService.getTickets()
      .subscribe({
        next: tickets => this.tickets = tickets,
        complete: () => this.addAssignees(this.tickets)
      });
  }

  addAssignees(tickets: Ticket[]): void {
    tickets.forEach(value => {
      let assignee: User;
      let tester: User;
      let category: Category;
      this.userService.getAssigneeByTicketId(value.id).subscribe({
        next: assi => assignee = assi,
        complete: () => this.assigneeMap.set(value.id, assignee)
      });
      this.userService.getTesterByTicketId(value.id).subscribe({
        next: test => tester = test,
        complete: () => this.testerMap.set(value.id, tester)
      });
      this.categoryService.getCategoryByTicketId(value.id).subscribe({
        next: cat => category = cat,
        complete: () => this.categoryMap.set(value.id, category)
      });
    });
    console.log('assignees:', this.assigneeMap);
    console.log('testers:', this.testerMap);
    console.log('categories:', this.categoryMap);
  }
}
