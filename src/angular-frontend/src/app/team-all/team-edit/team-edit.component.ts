import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { delay } from 'rxjs';
import { Location } from "@angular/common";
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Team } from '../team';
import { TeamService } from '../team.service';
import { AuthenticationService } from 'src/app/security/authentication.service';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['../../../styles.css']
})
export class TeamEditComponent implements OnInit {

  @Input() team?: Team;
  members?: User[];

  constructor(
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private router: Router,
    private teamService: TeamService,
    private userService: UserService,
    private location: Location) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAdmin() || this.authenticationService.isUserTeamModerator()) {
      this.getTeam();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getTeam(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.teamService.getTeam(+id)
        .subscribe({
          next: team => this.team = team,
          complete: () => this.userService.getUsersByTeamId(this.team.id)
            .subscribe({
              next: members => this.members = members,
              error: () => console.log('Error in team-edit -> getUsersByTeamId')
            }),
          error: () => console.log('Error in team-edit -> getTeam')
        });

    } else {
      console.error('id can not be null!');
    }
  }

  update(name: string, description: string): void {
    name = name.trim();
    description = description.trim();

    if (!name || !description) {
      return;
    }

    var id: number = this.team.id;

    this.teamService.updateTeam({ id, name, description } as Team).subscribe({
      next: team => this.team = team,
      complete: () => this.router.navigate([`team/${this.team.id}`]),
      error: () => console.log('Error in team-edit -> updateTeam')
    });
  }

  goBack(): void {
    this.location.back();
  }

}
