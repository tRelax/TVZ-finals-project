import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";
import { Team } from 'src/app/team-all/team';
import { Ticket } from 'src/app/ticket-all/ticket';
import { TicketService } from 'src/app/ticket-all/ticket.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { TeamService } from 'src/app/team-all/team.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  @Input() user?: User;
  tickets?: Ticket[];
  teams?: Team[];

  constructor(
    private route: ActivatedRoute, 
    private userService: UserService, 
    private ticketService: TicketService, 
    private teamService: TeamService, 
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.userService.getUser(+id)
        .subscribe(user => {
          this.user = user;
          this.ticketService.getTicketsByUserId(user.id)
          .subscribe(
            tickets => this.tickets = tickets
          );
          this.teamService.getTeamsByUserId(user.id)
          .subscribe(
            teams => this.teams = teams
          );
        });
    } else {
      console.error('id can not be null!');
    }
  }

  goBack(): void {
    this.location.back();
  }
}
