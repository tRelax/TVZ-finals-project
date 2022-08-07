import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";
import { Ticket } from 'src/app/ticket-all/ticket';
import { TicketService } from 'src/app/ticket-all/ticket.service';
import { Category } from '../category';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
  styleUrls: ['../../../styles.css']
})
export class CategoryDetailComponent implements OnInit {

  @Input() category?: Category;
  tickets?: Ticket[];

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private ticketService: TicketService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getCategory();
  }

  getCategory(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.categoryService.getCategory(+id)
        .subscribe(category => {
          this.category = category;
          this.ticketService.getTicketsByCategoryId(category.id)
            .subscribe(
              tickets => this.tickets = tickets
            );
        });
    } else {
      console.error('id can not be null!');
    }
  }

  goBack(): void {
    this.location.back();
  }

}
