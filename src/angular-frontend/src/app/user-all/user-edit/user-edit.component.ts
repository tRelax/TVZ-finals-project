import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { delay } from 'rxjs';
import { Location } from "@angular/common";
import { Team } from 'src/app/team-all/team';
import { TeamService } from 'src/app/team-all/team.service';
import { Ticket } from 'src/app/ticket-all/ticket';
import { TicketService } from 'src/app/ticket-all/ticket.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { AuthenticationService } from 'src/app/security/authentication.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  @Input() user?: User;
  tickets?: Ticket[];
  teams?: Team[];

  constructor(
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private router: Router,
    private userService: UserService,
    private ticketService: TicketService,
    private teamService: TeamService,
    private location: Location) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.userService.getUser(+id)
        .subscribe(user => {
          if (this.authenticationService.getAuthenticatedUserUsername() != user.username) {
            this.router.navigate(['forbidden'])
          }
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

  update(username: string, name: string, surname: string): void {
    username = username.trim();
    name = name.trim();
    surname = surname.trim();

    if (!username || !name || !surname) {
      return;
    }

    var id: number = this.user.id;

    this.userService.updateUser({ id, username, name, surname } as User).subscribe(
      (user: User) => {
        this.user = user;
        console.log('Changes applied!');
        delay(2000);
        this.router.navigate(['user'])
      },
      () => {
        console.log('Error!');
      }
    )
  }

  goBack(): void {
    this.location.back();
  }

}
