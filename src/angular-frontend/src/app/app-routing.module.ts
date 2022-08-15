import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryAddComponent } from './category-all/category-add/category-add.component';
import { CategoryDetailComponent } from './category-all/category-detail/category-detail.component';
import { CategoryEditComponent } from './category-all/category-edit/category-edit.component';
import { CategoryComponent } from './category-all/category/category.component';
import { ForbiddenPageComponent } from './forbidden-page/forbidden-page.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RegisterComponent } from './register/register.component';
import { LoggedInGuard } from './security/logged-in.guard';
import { TeamAddComponent } from './team-all/team-add/team-add.component';
import { TeamDetailComponent } from './team-all/team-detail/team-detail.component';
import { TeamEditMembersComponent } from './team-all/team-edit-members/team-edit-members.component';
import { TeamEditComponent } from './team-all/team-edit/team-edit.component';
import { TeamComponent } from './team-all/teams/team.component';
import { TicketAddComponent } from './ticket-all/ticket-add/ticket-add.component';
import { TicketDetailComponent } from './ticket-all/ticket-detail/ticket-detail.component';
import { TicketEditComponent } from './ticket-all/ticket-edit/ticket-edit.component';
import { TicketComponent } from './ticket-all/ticket/ticket.component';
import { UserDetailComponent } from './user-all/user-detail/user-detail.component';
import { UserEditComponent } from './user-all/user-edit/user-edit.component';
import { UserComponent } from './user-all/users/user.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'ticket',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'user/:id',
    component: UserDetailComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'user/edit/:id',
    component: UserEditComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'team',
    component: TeamComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'team/:id',
    component: TeamDetailComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'team/edit/:id',
    component: TeamEditComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'team/edit/members/:id',
    component: TeamEditMembersComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'add/team',
    component: TeamAddComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'category',
    component: CategoryComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'category/:id',
    component: CategoryDetailComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'category/edit/:id',
    component: CategoryEditComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'add/category',
    component: CategoryAddComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'ticket',
    component: TicketComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'ticket/:id',
    component: TicketDetailComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'ticket/edit/:id',
    component: TicketEditComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'add/ticket',
    component: TicketAddComponent,
    canActivate: [LoggedInGuard]
  },
  {
    path: 'forbidden',
    component: ForbiddenPageComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
