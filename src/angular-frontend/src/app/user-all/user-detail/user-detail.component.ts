import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from "@angular/common";
import { Team } from 'src/app/team-all/team';
import { Ticket } from 'src/app/ticket-all/ticket';
import { TicketService } from 'src/app/ticket-all/ticket.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { TeamService } from 'src/app/team-all/team.service';
import { AuthenticationService } from 'src/app/security/authentication.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['../../../styles.css']
})
export class UserDetailComponent implements OnInit {

  @Input() user?: User;
  tickets?: Ticket[];
  teams?: Team[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private ticketService: TicketService,
    private teamService: TeamService,
    public authenticationService: AuthenticationService,
    private location: Location
  ) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getUser();
    } else {
      this.router.navigate(['forbidden'])
    }
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
