import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatePipe, Location } from "@angular/common";
import { Category } from 'src/app/category-all/category';
import { CategoryService } from 'src/app/category-all/category.service';
import { Team } from 'src/app/team-all/team';
import { TeamService } from 'src/app/team-all/team.service';
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Ticket } from '../ticket';
import { TicketService } from '../ticket.service';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['../../../styles.css']
})
export class TicketDetailComponent implements OnInit {

  @Input() ticket?: Ticket;
  category?: Category;
  assignee?: User;
  tester?: User;
  team?: Team;
  start_date: String;
  due_date: String;

  constructor(
    private route: ActivatedRoute,
    private ticketService: TicketService,
    private categoryService: CategoryService,
    private teamService: TeamService,
    private userService: UserService,
    private location: Location,
    public datepipe: DatePipe
  ) { }

  ngOnInit(): void {
    this.getTicket();
  }

  getTicket(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.ticketService.getTicket(+id)
        .subscribe(ticket => {
          this.ticket = ticket;
          this.categoryService.getCategoryByTicketId(+ticket.id)
            .subscribe(
              category => this.category = category
            );
          this.userService.getAssigneeByTicketId(+ticket.id)
            .subscribe(
              assignee => this.assignee = assignee
            );
          this.userService.getTesterByTicketId(+ticket.id)
            .subscribe(
              tester => this.tester = tester
            );
          this.teamService.getTeamByTicketId(+ticket.id)
            .subscribe(
              team => this.team = team
            );
          this.dateTransform();
        });
    } else {
      console.error('id can not be null!');
    }
  }

  goBack(): void {
    this.location.back();
  }

  dateTransform() {
    this.start_date = this.datepipe.transform(this.ticket.start_date, 'yyyy-MM-dd');
    this.due_date = this.datepipe.transform(this.ticket.due_date, 'yyyy-MM-dd');
  }

}
