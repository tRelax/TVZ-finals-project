import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
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

  constructor(
    private userService: UserService,
    private router: Router
  ) { 
    
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

}
