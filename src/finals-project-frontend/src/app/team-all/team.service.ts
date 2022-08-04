import { Injectable } from '@angular/core';
import {catchError, Observable, of, tap} from "rxjs";
import { Team } from './team';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamURL = 'http://localhost:8080/team';
  
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) { }

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.teamURL)
      .pipe(
        tap(_ => console.log('fetched teams')),
        catchError(this.handleError<Team[]>('getTeams', []))
      )
  }

  getTeam(id: number): Observable<Team> {
    const url = `${this.teamURL}/${id}`;
    return this.http.get<Team>(url)
      .pipe(
        tap(_ => console.log(`fetched team w/ id=${id}`)),
        catchError(this.handleError<Team>(`getTeam id=${id}`))
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
