import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from "rxjs";
import { Ticket } from './ticket';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private ticketURL = 'http://localhost:8080/tickets';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
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

  getTicketsByUserId(id: number): Observable<Ticket[]> {
    const params = new HttpParams().set('assignee_id', id);

    return this.http.get<Ticket[]>(this.ticketURL, { params })
      .pipe(
        tap(_ => console.log(`fetched tickets with assignee_id=${id}`,)),
        catchError(this.handleError<Ticket[]>(`getTicketsByUserId id=${id}`, []))
      );
  }

  getTicketsByCategoryId(id: number): Observable<Ticket[]> {
    const params = new HttpParams().set('category_id', id);

    return this.http.get<Ticket[]>(this.ticketURL, { params })
      .pipe(
        tap(_ => console.log(`fetched tickets with category_id=${id}`,)),
        catchError(this.handleError<Ticket[]>(`getTicketsByCategoryId id=${id}`, []))
      );
  }

  updateTicket(ticket: Ticket): Observable<any> {
    const url = `${this.ticketURL}/${ticket.id}`;
    return this.http.put(url, ticket)
      .pipe(
        tap(_ => console.log(`updated ticket with id=${ticket.id}`)),
        catchError(this.handleError<any>('updateTicket'))
      )
  }

  updateTicketsAssignee(ticket_id: number, assignee_id: number): Observable<any> {
    const params = new HttpParams().set('assignee_id', assignee_id);
    return this.http.patch(this.ticketURL, ticket_id, { params })
      .pipe(
        tap(_ => console.log(`updated assignee for ticket with id=${ticket_id}`)),
        catchError(this.handleError<any>('updateTicketsAssignee'))
      )
  }

  updateTicketsTester(ticket_id: number, tester_id: number): Observable<any> {
    const params = new HttpParams().set('tester_id', tester_id);
    return this.http.patch(this.ticketURL, ticket_id, { params })
      .pipe(
        tap(_ => console.log(`updated tester for ticket with id=${ticket_id}`)),
        catchError(this.handleError<any>('updateTicketsTester'))
      )
  }

  updateTicketCategory(ticket_id: number, category_id: number): Observable<any> {
    const url = `${this.ticketURL}/?category_id=${category_id}`;
    return this.http.patch(url, ticket_id)
      .pipe(
        tap(_ => console.log(`updated category for ticket with id=${ticket_id}`)),
        catchError(this.handleError<any>('updateTicketCategory'))
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
