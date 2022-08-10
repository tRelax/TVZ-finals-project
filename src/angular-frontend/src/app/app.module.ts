import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { DatePipe } from '@angular/common'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TeamComponent } from './team-all/teams/team.component';
import { UserComponent } from './user-all/users/user.component';
import { CategoryComponent } from './category-all/category/category.component';
import { TicketComponent } from './ticket-all/ticket/ticket.component';
import { UserDetailComponent } from './user-all/user-detail/user-detail.component';
import { TeamDetailComponent } from './team-all/team-detail/team-detail.component';
import { TicketDetailComponent } from './ticket-all/ticket-detail/ticket-detail.component';
import { CategoryDetailComponent } from './category-all/category-detail/category-detail.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserEditComponent } from './user-all/user-edit/user-edit.component';
import { CategoryEditComponent } from './category-all/category-edit/category-edit.component';
import { TeamEditComponent } from './team-all/team-edit/team-edit.component';
import { TicketEditComponent } from './ticket-all/ticket-edit/ticket-edit.component';
import { TeamEditMembersComponent } from './team-all/team-edit-members/team-edit-members.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';
import { ForbiddenPageComponent } from './forbidden-page/forbidden-page.component';
import { AuthenticationInterceptor } from './security/authentication.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    TeamComponent,
    CategoryComponent,
    TicketComponent,
    UserDetailComponent,
    TeamDetailComponent,
    TicketDetailComponent,
    CategoryDetailComponent,
    UserEditComponent,
    CategoryEditComponent,
    TeamEditComponent,
    TicketEditComponent,
    TeamEditMembersComponent,
    PageNotFoundComponent,
    LoginComponent,
    ForbiddenPageComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
