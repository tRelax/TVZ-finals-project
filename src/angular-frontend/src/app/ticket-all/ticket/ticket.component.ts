import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/category-all/category';
import { CategoryService } from 'src/app/category-all/category.service';
import { AuthenticationService } from 'src/app/security/authentication.service';
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
  query: string;

  constructor(
    private ticketService: TicketService,
    private userService: UserService,
    private categoryService: CategoryService,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {

  }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getTickets();
    } else {
      this.router.navigate(['forbidden'])
    }
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
      this.userService.getAssigneeByTicketId(value.id).subscribe({
        next: assi => this.assigneeMap.set(value.id, assi)
      });
      this.userService.getTesterByTicketId(value.id).subscribe({
        next: test => this.testerMap.set(value.id, test)
      });
      this.categoryService.getCategoryByTicketId(value.id).subscribe({
        next: cat => this.categoryMap.set(value.id, cat)
      });
    });
    console.log('assignees:', this.assigneeMap);
    console.log('testers:', this.testerMap);
    console.log('categories:', this.categoryMap);
  }
}
