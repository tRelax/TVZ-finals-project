import {
    AbstractControl,
    AsyncValidatorFn,
    ValidationErrors,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import { Team } from './team';
import { TeamService } from './team.service';

export class TeamNameValidator {
    static createValidator(teamService: TeamService, teams: Team[]): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors> => {
            return teamService
                .checkIfNameExists(control.value, teams)
                .pipe(
                    map((result: boolean) =>
                        result ? { usernameAlreadyExists: true } : null
                    )
                );
        };
    }
}