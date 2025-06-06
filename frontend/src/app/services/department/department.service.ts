import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DepartmentDto } from '../../models/department.dto';
import { Department } from '../../models/department.model';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private readonly http = inject(HttpClient);

  getDepartments(): Observable<Array<DepartmentDto>> {
    return this.http.get<Array<DepartmentDto>>(`${environment.apiUrl}/v1/departments`);
  }

  deleteDepartment(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/v1/departments/${id}`);
  }

  createDepartment(data: Department): Observable<Department> {
    return this.http.post<Department>(`${environment.apiUrl}/v1/departments`, data);
  }
}
