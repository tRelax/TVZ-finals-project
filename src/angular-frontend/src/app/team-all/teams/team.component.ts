import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/security/authentication.service';
import { Team } from '../team';
import { TeamService } from '../team.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['../../../styles.css']
})
export class TeamComponent implements OnInit {

  teams: Team[];
  isUserAdmin: boolean;
  query: string;

  constructor(
    private teamService: TeamService,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {

  }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getTeams();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getTeams(): void {
    this.teamService.getTeams()
      .subscribe(teams => this.teams = teams);
  }

}
