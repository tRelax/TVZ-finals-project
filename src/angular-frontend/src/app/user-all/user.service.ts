import { Injectable } from '@angular/core';
import {catchError, Observable, of, tap} from "rxjs";
import {User} from "./user";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userURL = 'http://localhost:8080/user';
  
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
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

    return this.http.get<User[]>(this.userURL, {params})
      .pipe(
        tap(_ => console.log(`fetched users with team_id=${id}`,)),
        catchError(this.handleError<User[]>(`getUserByTeamId id=${id}`, []))
      );
  }

  getAssigneeByTicketId(id: number): Observable<User> {
    const params = new HttpParams().set('assignee_ticket_id', id);

    return this.http.get<User>(this.userURL, {params})
      .pipe(
        tap(_ => console.log(`fetched assignee with ticket_id=${id}`,)),
        catchError(this.handleError<User>(`getAssigneeByTicketId id=${id}`))
      );
  }

  getTesterByTicketId(id: number): Observable<User> {
    const params = new HttpParams().set('tester_ticket_id', id);

    return this.http.get<User>(this.userURL, {params})
      .pipe(
        tap(_ => console.log(`fetched tester with ticket_id=${id}`,)),
        catchError(this.handleError<User>(`getTesterByTicketId id=${id}`))
      );
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(operation);
      console.error(error);
      return of(result as T);
    };
  }
}
