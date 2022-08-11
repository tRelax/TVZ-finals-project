import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './security/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  links = [
    {
      label: "Users",
      link: "/user"
    },
    {
      label: "Teams",
      link: "/team"
    }, {
      label: "Categories",
      link: "/category"
    }, {
      label: "Tickets",
      link: "/ticket"
    }
  ]
  title = 'Tickets Via Zagreb';
  isUserAdmin: boolean;
  isUserTeamModerator: boolean;

  constructor(
    public authenticationService: AuthenticationService,
    private router: Router
  ) {
    this.isUserAdmin = this.authenticationService.isUserAdmin();
    this.isUserTeamModerator = this.authenticationService.isUserTeamModerator();
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']).then();
  }
}
