import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Category } from 'src/app/category-all/category';
import { CategoryService } from 'src/app/category-all/category.service';
import { AuthenticationService } from 'src/app/security/authentication.service';
import { Team } from 'src/app/team-all/team';
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Ticket } from '../ticket';
import { TicketService } from '../ticket.service';

@Component({
  selector: 'app-ticket-add',
  templateUrl: './ticket-add.component.html',
  styleUrls: ['../../../styles.css']
})
export class TicketAddComponent implements OnInit {

  categories?: Category[];
  users?: User[];
  first_user?: User;
  private initialState = {
    name: '',
    description: '',
    assignee: '',
    tester: '',
    category: '',
    due_date: '',
    priority: ''
  };
  due_date_start: String;

  ticketForm: FormGroup;

  priorityValues = [
    {
      id: 0,
      value: 0
    },
    {
      id: 1,
      value: 10
    },
    {
      id: 2,
      value: 20
    },
    {
      id: 3,
      value: 30
    },
    {
      id: 4,
      value: 40
    },
    {
      id: 5,
      value: 50
    },
    {
      id: 6,
      value: 60
    },
    {
      id: 7,
      value: 70
    },
    {
      id: 8,
      value: 80
    },
    {
      id: 9,
      value: 90
    },
    {
      id: 10,
      value: 100
    }
  ]

  constructor(
    private fb: FormBuilder,
    public authenticationService: AuthenticationService,
    private router: Router,
    private userService: UserService,
    private categoryService: CategoryService,
    private ticketService: TicketService
  ) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getCategoriesAndUsers();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getCategoriesAndUsers(): void {
    this.categoryService.getCategories()
      .subscribe({
        next: categories => this.categories = categories,
        complete: () => this.getUsers()
      });
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe({
        next: users => this.users = users,
        complete: () => this.createForm()
      });
  }

  createForm() {
    this.due_date_start = new Date().toISOString().split('T')[0];
    this.first_user = this.users[0];
    this.ticketForm = this.fb.group({
      name: [
        this.initialState.name,
        [Validators.required, Validators.maxLength(100)]
      ],
      description: [
        this.initialState.description,
        [Validators.required, Validators.maxLength(200)]
      ],
      assignee: [],
      tester: [],
      category: [],
      due_date: [],
      priority: []
    });
    this.ticketForm.controls['assignee'].setValue(this.users[0].id);
    this.ticketForm.controls['tester'].setValue(this.users[0].id);
    this.ticketForm.controls['category'].setValue(this.categories[0].id);
    this.ticketForm.controls['due_date'].setValue(this.due_date_start);
    this.ticketForm.controls['priority'].setValue(this.priorityValues[0].id);
  }

  onSubmit() {
    console.log(this.ticketForm.get("name").value);
    console.log(this.ticketForm.get("description").value);
    console.log(this.ticketForm.get("assignee").value);
    console.log(this.ticketForm.get("tester").value);
    console.log(this.ticketForm.get("category").value);
    console.log(this.ticketForm.get("due_date").value);
    console.log(this.ticketForm.get("priority").value);
    var id = 1;
    var name = this.ticketForm.get("name").value;
    var description = this.ticketForm.get("description").value;
    var assignee = this.ticketForm.get("assignee").value;
    var tester = this.ticketForm.get("tester").value;
    var category = this.ticketForm.get("category").value;
    var start_date: Date = new Date();
    var due_date = this.ticketForm.get("due_date").value;
    var priority = this.ticketForm.get("priority").value;
    var progress = 0;
    this.ticketService.addTicket({ id, name, description, start_date, due_date, progress, priority } as Ticket,
      assignee,
      tester,
      category)
      .subscribe({
        next: () => alert("Successfully added ticket!"),
        complete: () => this.router.navigate(['ticket'])
      });
  }

  get name() { return this.ticketForm.get('name')!; }
  get description() { return this.ticketForm.get('description')!; }
  get assignee() { return this.ticketForm.get('assignee')!; }
  get tester() { return this.ticketForm.get('tester')!; }
  get category() { return this.ticketForm.get('category')!; }
  get due_date() { return this.ticketForm.get('due_date')!; }
  get priority() { return this.ticketForm.get('priority')!; }

}
