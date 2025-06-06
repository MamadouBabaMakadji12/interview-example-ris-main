import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CreateEmployee } from '../../models/create.employee.dto';
import { Employee } from '../../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private http = inject(HttpClient);

  findEmployeesByDepartmentName(name: string): Observable<Array<Employee>> {
    return this.http.get<Array<Employee>>(`${environment.apiUrl}/v1/employees/department/name/${name}`);
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/v1/employees/${id}`);
  }

  createEmployee(newEmployee: CreateEmployee): Observable<Employee> {
    return this.http.post<Employee>(`${environment.apiUrl}/v1/employees`, newEmployee);
  }

  findEmployeesById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${environment.apiUrl}/v1/employees/${id}`);
  }

  findEmployeesByFullName(keyword: string): Observable<Array<Employee>> {
    return this.http.get<Array<Employee>>(`${environment.apiUrl}/v1/employees/search?keyword=${keyword}`);
  }
}
