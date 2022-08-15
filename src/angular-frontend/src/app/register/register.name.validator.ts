import {
    AbstractControl,
    AsyncValidatorFn,
    ValidationErrors,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import { User } from '../user-all/user';
import { UserService } from '../user-all/user.service';

export class RegisterNameValidator {
    static createValidator(userService: UserService, users: User[]): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors> => {
            return userService
                .checkIfNameExists(control.value, users)
                .pipe(
                    map((result: boolean) =>
                        result ? { usernameAlreadyExists: true } : null
                    )
                );
        };
    }
}