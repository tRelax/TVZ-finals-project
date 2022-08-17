import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/security/authentication.service';
import { Category } from '../category';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['../../../styles.css']
})
export class CategoryComponent implements OnInit {

  categories: Category[];
  isUserAdmin: boolean;
  query: string;

  constructor(
    private categoryService: CategoryService,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
    if (this.authenticationService.isUserAuthenticated()) {
      this.getCategories();
    } else {
      this.router.navigate(['forbidden'])
    }
  }

  getCategories(): void {
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories);
  }

}
