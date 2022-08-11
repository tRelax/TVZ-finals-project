import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from "rxjs";
import { User } from "./user";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userURL = 'http://localhost:8080/user';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userURL)
      .pipe(
        tap(_ => console.log('fetched users')),
        catchError(this.handleError<User[]>('getUsers', []))
      )
  }

  getUser(id: number): Observable<User> {
    const url = `${this.userURL}/${id}`;
    return this.http.get<User>(url)
      .pipe(
        tap(_ => console.log(`fetched user w/ id=${id}`)),
        catchError(this.handleError<User>(`getUser id=${id}`))
      );
  }

  getUsersByTeamId(id: number): Observable<User[]> {
    const params = new HttpParams().set('team_id', id);

    return this.http.get<User[]>(this.userURL, { params })
      .pipe(
        tap(_ => console.log(`fetched users with team_id=${id}`,)),
        catchError(this.handleError<User[]>(`getUserByTeamId id=${id}`, []))
      );
  }

  getAssigneeByTicketId(id: number): Observable<User> {
    const params = new HttpParams().set('assignee_ticket_id', id);

    return this.http.get<User>(this.userURL, { params })
      .pipe(
        tap(_ => console.log(`fetched assignee with ticket_id=${id}`,)),
        catchError(this.handleError<User>(`getAssigneeByTicketId id=${id}`))
      );
  }

  getTesterByTicketId(id: number): Observable<User> {
    const params = new HttpParams().set('tester_ticket_id', id);

    return this.http.get<User>(this.userURL, { params })
      .pipe(
        tap(_ => console.log(`fetched tester with ticket_id=${id}`,)),
        catchError(this.handleError<User>(`getTesterByTicketId id=${id}`))
      );
  }

  addUser(user: User): Observable<any> {
    return this.http.post<User>(this.userURL, user)
      .pipe(
        tap(_ => console.log(`adding new user`,)),
        catchError(this.handleError<User>(`addUser`))
      );
  }

  updateUser(user: User): Observable<any> {
    const url = `${this.userURL}/${user.id}`;
    return this.http.put(url, user)
      .pipe(
        tap(_ => console.log(`updated user with id=${user.id}`)),
        catchError(this.handleError<any>('updateUser'))
      )
  }

  updateUserTeamAdd(user_id: number, team_id: number): Observable<any> {
    const url = `${this.userURL}/?add_id=${user_id}`;
    return this.http.patch(url, team_id)
      .pipe(
        tap(_ => console.log(`updated user with id=${user_id}`)),
        catchError(this.handleError<any>('updateUserTeamAdd'))
      )
  }

  updateUserTeamRemove(user_id: number, team_id: number): Observable<any> {
    const url = `${this.userURL}/?remove_id=${user_id}`;
    return this.http.patch(url, team_id)
      .pipe(
        tap(_ => console.log(`updated user with id=${user_id}`)),
        catchError(this.handleError<any>('updateUserTeamRemove'))
      )
  }

  updateTicketListAssigneeAdd(assignee_id: number, past_assignee_id: number, ticket_id: number): Observable<any> {
    if (assignee_id == past_assignee_id) {
      console.log("Selected user and new user are the same");
      return;
    }
    const params = new HttpParams().set('assignee_add_id', assignee_id);
    return this.http.patch(this.userURL, ticket_id, { params })
      .pipe(
        tap(_ => console.log(`updated user with id=${assignee_id}`)),
        catchError(this.handleError<any>('updateTicketListAssigneeAdd'))
      )
  }

  updateTicketListAssigneeRemove(assignee_id: number, past_assignee_id: number, ticket_id: number): Observable<any> {
    if (assignee_id == past_assignee_id) {
      console.log("Selected user and new user are the same!");
      return;
    }
    const params = new HttpParams().set('assignee_remove_id', past_assignee_id);
    return this.http.patch(this.userURL, ticket_id, { params })
      .pipe(
        tap(_ => console.log(`updated user with id=${past_assignee_id}`)),
        catchError(this.handleError<any>('updateTicketListAssigneeRemove'))
      )
  }

  updateTicketListTesterAdd(tester_id: number, past_tester_id: number, ticket_id: number): Observable<any> {
    if (tester_id == past_tester_id) {
      console.log("Selected user and new user are the same");
      return;
    }
    const params = new HttpParams().set('tester_add_id', tester_id);
    return this.http.patch(this.userURL, ticket_id, { params })
      .pipe(
        tap(_ => console.log(`updated tester with id=${tester_id} [ADD]`)),
        catchError(this.handleError<any>('updateTicketListTesterAdd'))
      )
  }

  updateTicketListTesterRemove(tester_id: number, past_tester_id: number, ticket_id: number): Observable<any> {
    if (tester_id == past_tester_id) {
      console.log("Selected user and new user are the same!");
      return;
    }
    const params = new HttpParams().set('tester_remove_id', past_tester_id);
    return this.http.patch(this.userURL, ticket_id, { params })
      .pipe(
        tap(_ => console.log(`updated tester with id=${past_tester_id} [REMOVE]`)),
        catchError(this.handleError<any>('updateTicketListTesterRemove'))
      )
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(operation);
      console.error(error);
      return of(result as T);
    };
  }
}
