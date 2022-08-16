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

  updateTicket(ticket: Ticket, assignee_id: number, tester_id: number, category_id: number): Observable<any> {
    const url = `${this.ticketURL}/updateTicket/?ticket_id=${ticket.id}&assignee_id=${assignee_id}&tester_id=${tester_id}&category_id=${category_id}`;
    return this.http.put(url, ticket)
      .pipe(
        tap(_ => console.log(`updated ticket with id=${ticket.id}`)),
        catchError(this.handleError<any>('updateTicket'))
      )
  }

  addTicket(ticket: Ticket, assignee_id: number, tester_id: number, category_id: number): Observable<any> {
    const url = `${this.ticketURL}/addTicket/?assignee_id=${assignee_id}&tester_id=${tester_id}&category_id=${category_id}`;
    return this.http.post(url, ticket).pipe(
      tap(_ => console.log(`added new ticket`)),
      catchError(this.handleError<any>('addTicket'))
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
