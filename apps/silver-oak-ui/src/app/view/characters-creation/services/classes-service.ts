import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ClassesService {
  private http = inject(HttpClient)

  getClasses() {
    return this.http.get<string[]>('http://localhost:8080/api/classes');
  }
}
