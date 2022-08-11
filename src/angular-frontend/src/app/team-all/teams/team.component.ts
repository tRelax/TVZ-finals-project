import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
    private router: Router
  ) {

  }

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams(): void {
    this.teamService.getTeams()
      .subscribe(teams => this.teams = teams);
  }

}
