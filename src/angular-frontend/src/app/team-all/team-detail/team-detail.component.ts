import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Team } from '../team';
import { TeamService } from '../team.service';

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
    private teamService: TeamService,
    private userService: UserService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getTeam();
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
    } else {
      console.error('id can not be null!');
    }
  }

  goBack(): void {
    this.location.back();
  }
}
