import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryDetailComponent } from './category-all/category-detail/category-detail.component';
import { CategoryEditComponent } from './category-all/category-edit/category-edit.component';
import { CategoryComponent } from './category-all/category/category.component';
import { TeamDetailComponent } from './team-all/team-detail/team-detail.component';
import { TeamEditComponent } from './team-all/team-edit/team-edit.component';
import { TeamComponent } from './team-all/teams/team.component';
import { TicketDetailComponent } from './ticket-all/ticket-detail/ticket-detail.component';
import { TicketEditComponent } from './ticket-all/ticket-edit/ticket-edit.component';
import { TicketComponent } from './ticket-all/ticket/ticket.component';
import { UserDetailComponent } from './user-all/user-detail/user-detail.component';
import { UserEditComponent } from './user-all/user-edit/user-edit.component';
import { UserComponent } from './user-all/users/user.component';

const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'user/:id', component: UserDetailComponent },
  { path: 'user/edit/:id', component: UserEditComponent },
  { path: 'team', component: TeamComponent },
  { path: 'team/:id', component: TeamDetailComponent },
  { path: 'team/edit/:id', component: TeamEditComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'category/:id', component: CategoryDetailComponent },
  { path: 'category/edit/:id', component: CategoryEditComponent },
  { path: 'ticket', component: TicketComponent },
  { path: 'ticket/:id', component: TicketDetailComponent },
  { path: 'ticket/edit/:id', component: TicketEditComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
