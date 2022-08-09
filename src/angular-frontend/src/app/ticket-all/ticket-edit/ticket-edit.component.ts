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
            .subscribe(
              category => this.category = category
            );
          this.categoryService.getCategories()
            .subscribe(
              categories => this.categories = categories
            );
          this.userService.getAssigneeByTicketId(+ticket.id)
            .subscribe(
              assignee => this.assignee = assignee
            );
          this.userService.getTesterByTicketId(+ticket.id)
            .subscribe(
              tester => this.tester = tester
            );
          this.userService.getUsers()
            .subscribe(
              users => this.users = users
            );
          this.dateTransform();
        });
    } else {
      console.error('id can not be null!');
    }
  }

  dateTransform() {
    this.due_date_start = new Date().toISOString().split('T')[0];
    this.due_date = this.datepipe.transform(this.ticket.due_date, 'yyyy-dd-MM');
  }

  updateTicket(name: string, description: string, assignee_id: number, tester_id: number,
    category_id: number, due_date_value: String, priority: number, progress: number): void {

    name = name.trim();
    description = description.trim();

    if (!name || !description || !assignee_id || !tester_id || !category_id || !due_date_value
      || !priority || !progress) {
      return;
    }
    console.log("Update ticket params: ", name, description, assignee_id, tester_id,
      category_id, due_date_value, priority, progress);

    var id: number = this.ticket.id;
    var curAssignee: User = this.assignee;
    var curTester: User = this.tester;
    var curCategory: Category = this.category;
    var start_date: Date = this.ticket.start_date;
    var due_date: Date = new Date(due_date_value.toString());
    console.log("Formated due date: ", due_date);

    //ASSIGNEE
    this.userService.updateTicketListAssigneeAdd(assignee_id, curAssignee.id, id).subscribe(
      (user: User) => {
        console.log('Current assignee added!');
      },
      () => {
        console.log('Error!');
      }
    )

    this.userService.updateTicketListAssigneeRemove(assignee_id, curAssignee.id, id).subscribe(
      (user: User) => {
        console.log('Old assignee removed!');
      },
      () => {
        console.log('Error!');
      }
    )

    this.ticketService.updateTicketsAssignee(id, assignee_id).subscribe(
      (ticket: Ticket) => {
        this.ticket = ticket;
        console.log('Changed ticket assignee!');
      },
      () => {
        console.log('Error!');
      }
    )

    //TESTER
    this.userService.updateTicketListTesterAdd(tester_id, curTester.id, id).subscribe(
      (user: User) => {
        console.log('Current tester added!');
      },
      () => {
        console.log('Error!');
      }
    )

    this.userService.updateTicketListTesterRemove(tester_id, curTester.id, id).subscribe(
      (user: User) => {
        console.log('Old tester removed!');
      },
      () => {
        console.log('Error!');
      }
    )

    this.ticketService.updateTicketsTester(id, tester_id).subscribe(
      (ticket: Ticket) => {
        this.ticket = ticket;
        console.log('Changed ticket tester!');
      },
      () => {
        console.log('Error!');
      }
    )

    //CATEGORY
    this.categoryService.updateCategoryTicketListAdd(category_id, id).subscribe(
      (category: Category) => {
        console.log('Category ticket list changed [ADD]!');
      },
      () => {
        console.log('Error!');
      }
    )
    this.categoryService.updateCategoryTicketListRemove(curCategory.id, id).subscribe(
      (category: Category) => {
        console.log('Category ticket list changed [REMOVE]!');
      },
      () => {
        console.log('Error!');
      }
    )

    this.ticketService.updateTicketCategory(id, category_id).subscribe(
      (ticket: Ticket) => {
        this.ticket = ticket;
        console.log('Changed category!');
      },
      () => {
        console.log('Error!');
      }
    )

    this.ticketService.updateTicket({ id, name, description, start_date, due_date, progress, priority } as Ticket).subscribe(
      (ticket: Ticket) => {
        this.ticket = ticket;
        console.log('Changed ticket object!');
        delay(5000);
        this.router.navigate(['ticket'])
      },
      () => {
        console.log('Error!');
      }
    )
  }

  goBack(): void {
    this.location.back();
  }
}
