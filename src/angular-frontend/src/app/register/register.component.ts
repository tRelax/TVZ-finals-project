import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user-all/user';
import { UserService } from '../user-all/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../../styles.css']
})
export class RegisterComponent implements OnInit {

  users?: User[];

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    document.getElementById("usedUsername").style.display = "none";
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  btnRegisterClick(username: string, password: string, name: string, surname: string): void {
    var id_all = this.users.map(a => Math.max(a.id));
    var usernameTaken = false;
    this.users.forEach(u => {
      if (u.username == username) {
        usernameTaken = true;
        console.log(`Username already exists! ${u.username} === ${username}`);
        document.getElementById("usedUsername").style.display = "";
      }
    })
    if (!usernameTaken) {
      document.getElementById("usedUsername").style.display = "none";
      var id = id_all[0];
      console.log('highest id =', id);
      var tempUser = { id, username, name, surname } as User;
      console.log(tempUser);

      this.userService.addUser(tempUser, password)
        .subscribe({
          next: () => alert("Successfully registered!"),
          complete: () => this.router.navigate(['login'])
        });
    }

  }

}
