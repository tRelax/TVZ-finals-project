import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/security/authentication.service';
import { Category } from '../category';
import { CategoryNameValidator } from '../category.name.validator';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category-add',
  templateUrl: './category-add.component.html',
  styleUrls: ['../../../styles.css']
})
export class CategoryAddComponent implements OnInit {

  categories?: Category[];

  private initialState = {
    name: '',
    description: '',
    members: []
  };

  categoryForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    public authenticationService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserAdmin()) {
      this.getCategories();
    }
  }

  getCategories(): void {
    this.categoryService.getCategories()
      .subscribe({
        next: categories => this.categories = categories,
        complete: () => this.createForm(this.categories)
      });
  }

  createForm(categories: Category[]) {
    this.categoryForm = this.fb.group({
      name: [
        this.initialState.name,
        [Validators.required, Validators.maxLength(45)],
        [CategoryNameValidator.createValidator(this.categoryService, categories)]
      ],
      description: [
        this.initialState.description,
        [Validators.required, Validators.maxLength(200)]
      ]
    });
  }

  onSubmit() {
    console.log("valid");
    console.log(this.categoryForm.get("name").value);
    console.log(this.categoryForm.get("description").value);
    var id = 1;
    var name = this.categoryForm.get("name").value;
    var description = this.categoryForm.get("description").value;
    this.categoryService.addCategory({ id, name, description } as Category).subscribe({
      next: () => alert("Successfully added category!"),
      complete: () => this.router.navigate(['ticket'])
    });
  }

  get name() { return this.categoryForm.get('name')!; }
  get description() { return this.categoryForm.get('description')!; }

}
