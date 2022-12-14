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
    const url = `${this.userURL}/getUsers`
    return this.http.get<User[]>(url)
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

  addUser(user: User, password: string): Observable<any> {
    const params = new HttpParams().set('password', password);
    const url = `${this.userURL}/addUser`;
    return this.http.post<User>(url, user, { params })
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

  checkIfNameExists(value: string, users: User[]) {
    var name;
    name = users.find(u => u.username === value);

    return of(name).pipe();
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(operation);
      console.error(error);
      return of(result as T);
    };
  }
}
