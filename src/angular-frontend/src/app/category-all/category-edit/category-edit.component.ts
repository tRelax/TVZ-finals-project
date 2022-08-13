import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { delay } from 'rxjs';
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
    if (this.authenticationService.isUserAdmin() || this.authenticationService.isUserTeamModerator()) {
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

    this.categoryService.updateCategory({ id, name, description } as Category).subscribe(
      (category: Category) => {
        this.category = category;
        console.log('Changes applied!');
        delay(2000);
        this.router.navigate([`category/${this.category.id}`])
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
