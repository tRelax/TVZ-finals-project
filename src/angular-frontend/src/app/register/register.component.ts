import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../user-all/user';
import { UserService } from '../user-all/user.service';
import { RegisterNameValidator } from './register.name.validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../../styles.css']
})
export class RegisterComponent implements OnInit {

  users?: User[];
  private initialState = {
    username: '',
    name: '',
    surname: '',
    password: ''
  };

  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe({
        next: users => this.users = users,
        complete: () => this.createForm(this.users)
      });
  }

  createForm(users: User[]) {
    this.registerForm = this.fb.group({
      username: [
        this.initialState.username,
        [Validators.required, Validators.minLength(4), Validators.maxLength(20)],
        [RegisterNameValidator.createValidator(this.userService, this.users)]
      ],
      name: [
        this.initialState.name,
        [Validators.required, Validators.maxLength(16)]
      ],
      surname: [
        this.initialState.surname,
        [Validators.required, Validators.maxLength(16)]
      ],
      password: [
        this.initialState.password,
        [Validators.required, Validators.minLength(4), Validators.maxLength(16)]
      ]
    });
  }

  btnRegisterClick(username: string, password: string, name: string, surname: string): void {
    var id_all = this.users.map(a => Math.max(a.id));

    var id = id_all[0];
    var tempUser = { id, username, name, surname } as User;

    this.userService.addUser(tempUser, password)
      .subscribe({
        next: () => alert("Successfully registered!"),
        complete: () => this.router.navigate(['login'])
      });

  }

  get username() { return this.registerForm.get('username')!; }
  get name() { return this.registerForm.get('name')!; }
  get surname() { return this.registerForm.get('surname')!; }
  get password() { return this.registerForm.get('password')!; }

}
