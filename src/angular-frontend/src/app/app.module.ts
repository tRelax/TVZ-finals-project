import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TeamComponent } from './team-all/teams/team.component';
import { UserComponent } from './user-all/users/user.component';
import { CategoryComponent } from './category-all/category/category.component';
import { TicketComponent } from './ticket-all/ticket/ticket.component';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    TeamComponent,
    CategoryComponent,
    TicketComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
