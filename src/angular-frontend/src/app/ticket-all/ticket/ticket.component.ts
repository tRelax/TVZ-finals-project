import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Ticket } from '../ticket';
import { TicketService } from '../ticket.service';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {

  tickets?: Ticket[];
  assigneeMap = new Map<Number, User>();
  testerMap = new Map<Number, User>();
  assignee?: User;
  tester?: User;
  isUserAdmin: boolean;

  constructor(
    private ticketService: TicketService,
    private userService: UserService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
    this.getTickets();
  }

  getTickets(): void {
    this.ticketService.getTickets()
      .subscribe(
        tickets => this.tickets = tickets
      );

    this.addAssignees();
    this.assigneeMap.forEach((value: User, key: number) => {
      console.log(key, value);
    });
  }

  addAssignees(): void {
    this.tickets.forEach(value => {
      let user: User;
      let userTester: User;
      this.userService.getAssigneeByTicketId(value.id).subscribe(
        (assignee) => user = assignee
      );
      this.userService.getTesterByTicketId(value.id).subscribe(
        (tester) => userTester = tester
      );
      this.assigneeMap.set(value.id, user)
      this.testerMap.set(value.id, userTester)
    });
  }
}
