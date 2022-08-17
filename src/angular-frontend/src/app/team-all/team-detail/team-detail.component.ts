import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from "@angular/common";
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Team } from '../team';
import { TeamService } from '../team.service';
import { AuthenticationService } from 'src/app/security/authentication.service';

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['../../../styles.css']
})
export class TeamDetailComponent implements OnInit {

  @Input() team?: Team;
  users?: User[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private teamService: TeamService,
    private userService: UserService,
    public authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getTeam();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getTeam(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.teamService.getTeam(+id)
        .subscribe(team => {
          this.team = team;
          this.userService.getUsersByTeamId(team.id)
            .subscribe(
              users => this.users = users
            );
        });
      this.teamService.getTeam(+id).subscribe({
        next: team => this.team = team,
        complete: () => this.userService.getUsersByTeamId(this.team.id)
          .subscribe({
            next: users => this.users = users,
            error: () => console.log("Error in team-detail -> getUsersByTeamId")
          }),
        error: () => console.log("Error in team-detail -> getTeam")
      })
    } else {
      console.error('id can not be null!');
    }
  }

  goBack(): void {
    this.location.back();
  }
}
