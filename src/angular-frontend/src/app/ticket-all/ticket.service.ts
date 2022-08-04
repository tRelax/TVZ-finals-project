import { Injectable } from '@angular/core';
import {catchError, Observable, of, tap} from "rxjs";
import { Ticket } from './ticket';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private ticketURL = 'http://localhost:8080/tickets';
  
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) { }

  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.ticketURL)
      .pipe(
        tap(_ => console.log('fetched tickets')),
        catchError(this.handleError<Ticket[]>('getTickets', []))
      )
  }

  getTicket(id: number): Observable<Ticket> {
    const url = `${this.ticketURL}/${id}`;
    return this.http.get<Ticket>(url)
      .pipe(
        tap(_ => console.log(`fetched ticket w/ id=${id}`)),
        catchError(this.handleError<Ticket>(`getTicket id=${id}`))
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
