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
    public authenticationService: AuthenticationService,
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
        .subscribe(team => {
          this.team = team;
          this.userService.getUsersByTeamId(team.id)
            .subscribe(
              users => this.users = users
            );
          this.userService.getUsers()
            .subscribe(
              allUsers => this.allUsers = allUsers
            );
        });
    } else {
      console.error('id can not be null!');
    }
  }

  addMember(id: number): void {
    this.userService.updateUserTeamAdd(id, this.team.id).subscribe(
      (user: User) => {
        console.log('Changes applied!');
      },
      () => {
        console.log('Error!');
      })
    this.teamService.updateTeamMembersAdd(id, this.team.id).subscribe(
      (team: Team) => {
        this.team = team;
        console.log('Changes applied!');
        delay(2000);
        this.router.navigate([`team/edit/${this.team.id}`])
      },
      () => {
        console.log('Error!');
      })
  }

  removeMember(id: number): void {
    this.userService.updateUserTeamRemove(id, this.team.id).subscribe(
      (user: User) => {
        console.log('Changes applied!');
      },
      () => {
        console.log('Error!');
      })
    this.teamService.updateTeamMembersRemove(id, this.team.id).subscribe(
      (team: Team) => {
        this.team = team;
        console.log('Changes applied!');
        delay(2000);
        this.router.navigate([`team/edit/${this.team.id}`])
      },
      () => {
        console.log('Error!');
      })
  }

  updateTicket(name: string, description: string): void {
    name = name.trim();
    description = description.trim();

    if (!name || !description) {
      return;
    }

    var id: number = this.team.id;

    this.teamService.updateTeam({ id, name, description } as Team).subscribe(
      (team: Team) => {
        this.team = team;
        console.log('Changes applied!');
        delay(2000);
        this.router.navigate(['team'])
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
