import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryComponent } from './category-all/category/category.component';
import { TeamComponent } from './team-all/teams/team.component';
import { TicketComponent } from './ticket-all/ticket/ticket.component';
import { UserComponent } from './user-all/users/user.component';

const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'team', component: TeamComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'ticket', component: TicketComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
