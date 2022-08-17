import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthenticationService } from "src/app/security/authentication.service";
import { User } from "src/app/user-all/user";
import { UserService } from "src/app/user-all/user.service";
import { Team } from "../team";
import { TeamService } from "../team.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TeamNameValidator } from '../team.name.validator';

@Component({
  selector: 'app-team-add',
  templateUrl: './team-add.component.html',
  styleUrls: ['../../../styles.css']
})
export class TeamAddComponent implements OnInit {

  teams?: Team[];
  users?: User[];

  private initialState = {
    name: '',
    description: '',
    members: []
  };

  teamForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private teamService: TeamService,
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAdmin()) {
      this.getTeams();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getTeams(): void {
    this.teamService.getTeams()
      .subscribe({
        next: teams => this.teams = teams,
        complete: () => this.createForm(this.teams)
      });
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  createForm(teams: Team[]) {
    this.teamForm = this.fb.group({
      name: [
        this.initialState.name,
        [Validators.required, Validators.minLength(4), Validators.maxLength(45)],
        [TeamNameValidator.createValidator(this.teamService, this.teams)]
      ],
      description: [
        this.initialState.description,
        [Validators.required, Validators.maxLength(200)]
      ],
      members: [
        this.initialState.members
      ]
    });
  }

  onSubmit() {
    console.log("valid");
    console.log(this.teamForm.get("name").value);
    console.log(this.teamForm.get("description").value);
    console.log(this.teamForm.get("members").value);
    var id = 1;
    var name = this.teamForm.get("name").value;
    var description = this.teamForm.get("description").value;
    this.teamService.addTeam({ id, name, description } as Team, this.teamForm.get("members").value).subscribe({
      next: () => alert("Successfully added team!"),
      complete: () => this.router.navigate(['ticket'])
    });
  }

  get name() { return this.teamForm.get('name')!; }
  get description() { return this.teamForm.get('description')!; }
  get members() { return this.teamForm.get('members')!; }
}


