import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryDetailComponent } from './category-all/category-detail/category-detail.component';
import { CategoryComponent } from './category-all/category/category.component';
import { TeamDetailComponent } from './team-all/team-detail/team-detail.component';
import { TeamComponent } from './team-all/teams/team.component';
import { TicketDetailComponent } from './ticket-all/ticket-detail/ticket-detail.component';
import { TicketComponent } from './ticket-all/ticket/ticket.component';
import { UserDetailComponent } from './user-all/user-detail/user-detail.component';
import { UserComponent } from './user-all/users/user.component';

const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'user/:id', component: UserDetailComponent },
  { path: 'team', component: TeamComponent },
  { path: 'team/:id', component: TeamDetailComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'category/:id', component: CategoryDetailComponent },
  { path: 'ticket/:id', component: TicketDetailComponent },
  { path: 'ticket', component: TicketComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
