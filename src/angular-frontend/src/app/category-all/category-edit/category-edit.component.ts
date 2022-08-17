import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from "@angular/common";
import { Category } from '../category';
import { CategoryService } from '../category.service';
import { AuthenticationService } from 'src/app/security/authentication.service';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['../../../styles.css']
})
export class CategoryEditComponent implements OnInit {

  @Input() category?: Category;

  constructor(
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private router: Router,
    private categoryService: CategoryService,
    private location: Location) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAdmin()) {
      this.getCategory();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getCategory(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.categoryService.getCategory(+id)
        .subscribe(category => {
          this.category = category;
        });
    } else {
      console.error('id can not be null!');
    }
  }

  update(name: string, description: string): void {
    name = name.trim();
    description = description.trim();

    if (!name || !description) {
      return;
    }

    var id: number = this.category.id;

    this.categoryService.updateCategory({ id, name, description } as Category).subscribe({
      next: category => this.category = category,
      complete: () => this.router.navigate([`category/${this.category.id}`]),
      error: () => console.log("Error in category-edit -> updateCategory")
    });
  }

  goBack(): void {
    this.location.back();
  }

}
