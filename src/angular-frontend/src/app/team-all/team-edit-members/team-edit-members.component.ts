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
  selector: 'app-team-edit-members',
  templateUrl: './team-edit-members.component.html',
  styleUrls: ['../../../styles.css']
})
export class TeamEditMembersComponent implements OnInit {

  @Input() team?: Team;
  allUsers?: User[];
  users?: User[];

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
      this.userService.getUsers()
        .subscribe({
          next: allUsers => this.allUsers = allUsers,
          error: () => console.error('Error in team-edit-members -> getUsers')
        });

      this.teamService.getTeam(+id)
        .subscribe({
          next: team => this.team = team,
          complete: () => this.userService.getUsersByTeamId(this.team.id).subscribe({
            next: users => this.users = users,
            error: () => console.error('Error in team-edit-members -> getUsersByTeamId')
          }),
          error: () => console.error('Error in team-edit-members -> getTeam')
        });

    } else {
      console.error('id can not be null!');
    }
  }

  addMember(id: number): void {
    this.teamService.updateTeamMembersAdd(id, this.team.id).subscribe({
      next: team => this.team = team,
      complete: () => location.reload(),
      error: () => console.log('Error in addMember')
    });
  }

  removeMember(id: number): void {
    this.teamService.updateTeamMembersRemove(id, this.team.id).subscribe({
      next: team => this.team = team,
      complete: () => location.reload(),
      error: () => console.log('Error in removeMember')
    });
  }

  goBack(): void {
    this.location.back();
  }

}
