import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { delay } from 'rxjs';
import { DatePipe, Location } from "@angular/common";
import { Category } from 'src/app/category-all/category';
import { CategoryService } from 'src/app/category-all/category.service';
import { User } from 'src/app/user-all/user';
import { UserService } from 'src/app/user-all/user.service';
import { Ticket } from '../ticket';
import { TicketService } from '../ticket.service';

@Component({
  selector: 'app-ticket-edit',
  templateUrl: './ticket-edit.component.html',
  styleUrls: ['../../../styles.css']
})
export class TicketEditComponent implements OnInit {

  @Input() ticket?: Ticket;
  categories?: Category[];
  category?: Category;
  users?: User[];
  assignee?: User;
  tester?: User;
  due_date: String;
  due_date_start: String;

  progressAndPriorityValues = [
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
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private ticketService: TicketService,
    private categoryService: CategoryService,
    private location: Location,
    public datepipe: DatePipe
  ) { }

  ngOnInit(): void {
    this.getTicket();
  }

  getTicket(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.ticketService.getTicket(+id)
        .subscribe(ticket => {
          this.ticket = ticket;
          this.categoryService.getCategoryByTicketId(+ticket.id)
            .subscribe({
              next: category => this.category = category
            });
          this.categoryService.getCategories()
            .subscribe({
              next: categories => this.categories = categories
            });
          this.userService.getAssigneeByTicketId(+ticket.id)
            .subscribe({
              next: assignee => this.assignee = assignee
            });
          this.userService.getTesterByTicketId(+ticket.id)
            .subscribe({
              next: tester => this.tester = tester
            });
          this.userService.getUsers()
            .subscribe({
              next: users => this.users = users
            });
          this.dateTransform();
        });
    } else {
      console.error('id can not be null!');
    }
  }

  dateTransform() {
    this.due_date_start = new Date().toISOString().split('T')[0];
    this.due_date = this.datepipe.transform(this.ticket.due_date, 'yyyy-MM-dd');
  }

  updateTicket(name: string, description: string, assignee_id: number, tester_id: number,
    category_id: number, due_date_value: String, priority: number, progress: number): void {

    name = name.trim();
    description = description.trim();

    if (!name || !description || !assignee_id || !tester_id || !category_id || !due_date_value
      || priority < 0 || priority > 10 || progress < 0 || progress > 10) {
      alert("Check if everything is filled properly!");
      return;
    }
    console.log(`name = ${name}, description = ${description}, assignee_id = ${assignee_id}, tester_id = ${tester_id}, 
    category_id = ${category_id}, due_date_value = ${due_date_value}, priority = ${priority}, progress = ${progress}`);

    var id: number = this.ticket.id;
    var curAssignee: User = this.assignee;
    var curTester: User = this.tester;
    var curCategory: Category = this.category;
    var start_date: Date = this.ticket.start_date;
    var due_date: Date = new Date(due_date_value.toString());
    console.log("Formated due date: ", due_date);

    console.log(id);
    console.log(curAssignee);
    console.log(curTester);
    console.log(curCategory);

    this.ticketService.updateTicket({ id, name, description, start_date, due_date, progress, priority } as Ticket,
      assignee_id, tester_id, category_id).subscribe({
        next: ticket => console.log(ticket)
      });
  }

  goBack(): void {
    this.location.back();
  }
}
