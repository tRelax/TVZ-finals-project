import { Component } from '@angular/core';

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
  title = 'finals-project-frontend';
}
