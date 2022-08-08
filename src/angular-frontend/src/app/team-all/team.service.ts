import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from "rxjs";
import { Team } from './team';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamURL = 'http://localhost:8080/team';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
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

  getTeamsByUserId(id: number): Observable<Team[]> {
    const params = new HttpParams().set('user_id', id);

    return this.http.get<Team[]>(this.teamURL, { params })
      .pipe(
        tap(_ => console.log(`fetched teams with user_id=${id}`,)),
        catchError(this.handleError<Team[]>(`getTeamsByUserId id=${id}`, []))
      );
  }

  getTeamByTicketId(id: number): Observable<Team> {
    const params = new HttpParams().set('ticket_id', id);

    return this.http.get<Team>(this.teamURL, { params })
      .pipe(
        tap(_ => console.log(`fetched teams with ticket_id=${id}`,)),
        catchError(this.handleError<Team>(`getTeamByTicketId id=${id}`))
      );
  }

  updateTeam(team: Team): Observable<any> {
    const url = `${this.teamURL}/${team.id}`;
    return this.http.put(url, team)
      .pipe(
        tap(_ => console.log(`updated team with id=${team.id}`)),
        catchError(this.handleError<any>('updateTeam'))
      )
  }

  updateTeamMembersAdd(user_id: number, team_id: number): Observable<any> {
    const url = `${this.teamURL}/?add_id=${team_id}`;
    return this.http.patch(url, user_id)
      .pipe(
        tap(_ => console.log(`updated team with id=${team_id}`)),
        catchError(this.handleError<any>('updateTeamMembers'))
      )
  }

  updateTeamMembersRemove(user_id: number, team_id: number): Observable<any> {
    const url = `${this.teamURL}/?remove_id=${team_id}`;
    return this.http.patch(url, user_id)
      .pipe(
        tap(_ => console.log(`updated team with id=${team_id}`)),
        catchError(this.handleError<any>('updateTeamMembersRemove'))
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
