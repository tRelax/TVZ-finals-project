import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AuthenticationService } from 'src/app/security/authentication.service';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  users: User[];
  isUserAdmin: boolean;
  query: string;

  constructor(
    private userService: UserService,
    private router: Router,
    public authenticationService: AuthenticationService
  ) {

  }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getUsers();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

}
