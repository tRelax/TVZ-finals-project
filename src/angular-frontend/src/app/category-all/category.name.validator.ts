import {
    AbstractControl,
    AsyncValidatorFn,
    ValidationErrors,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import { Category } from './category';
import { CategoryService } from './category.service';

export class CategoryNameValidator {
    static createValidator(categoryService: CategoryService, categories: Category[]): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors> => {
            return categoryService
                .checkIfNameExists(control.value, categories)
                .pipe(
                    map((result: boolean) =>
                        result ? { usernameAlreadyExists: true } : null
                    )
                );
        };
    }
}