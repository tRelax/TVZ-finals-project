import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from "rxjs";
import { Category } from './category';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private categoryURL = 'http://localhost:8080/category';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoryURL)
      .pipe(
        tap(_ => console.log('fetched categories')),
        catchError(this.handleError<Category[]>('getCategories', []))
      )
  }

  getCategory(id: number): Observable<Category> {
    const url = `${this.categoryURL}/${id}`;
    return this.http.get<Category>(url)
      .pipe(
        tap(_ => console.log(`fetched category w/ id=${id}`)),
        catchError(this.handleError<Category>(`getCategory id=${id}`))
      );
  }

  getCategoryByTicketId(id: number): Observable<Category> {
    const params = new HttpParams().set('ticket_id', id);
    return this.http.get<Category>(this.categoryURL, { params })
      .pipe(
        tap(_ => console.log(`fetched category w/ ticket_id=${id}`)),
        catchError(this.handleError<Category>(`getCategoryByTicketId id=${id}`))
      );
  }

  updateCategory(category: Category): Observable<any> {
    const url = `${this.categoryURL}/${category.id}`;
    return this.http.put(url, category)
      .pipe(
        tap(_ => console.log(`updated category with id=${category.id}`)),
        catchError(this.handleError<any>('updateCategory'))
      )
  }

  addCategory(category: Category): Observable<any> {
    const url = `${this.categoryURL}/addCategory`;
    return this.http.post<Category>(url, category)
      .pipe(
        tap(_ => console.log(`adding new category`,)),
        catchError(this.handleError<Category>(`addCategory`))
      );
  }

  checkIfNameExists(value: string, categories: Category[]) {
    var name;
    name = categories.find(c => c.name === value);

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
