import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeFormComponent } from '../employee-form/employee-form.component';
import { Employee } from '../models/employee.model';
import { EmployeeService } from '../services/employee/employee.service';

@Component({
  selector: 'app-department-view',
  standalone: false,
  templateUrl: './department-view.component.html',
  styleUrl: './department-view.component.scss'
})
export class DepartmentViewComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private dialog = inject(MatDialog);
  private employeeService = inject(EmployeeService);
  departmentName!: string;
  employees: WritableSignal<Array<Employee>> = signal([]);
  searchEmployees: WritableSignal<Array<Employee> | null> = signal(null);

  ngOnInit(): void {
    this.departmentName = this.route.snapshot.paramMap.get('name') ?? '';
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.findEmployeesByDepartmentName(this.departmentName).subscribe({
      next: value => {
        this.employees.set(value);
      }
    });
  }

  openEmployeeView(employee: Employee): void {
    this.router.navigateByUrl(`/employee-view/${employee.id}`);
  }

  deleteEmployee(employee: Employee): void {
    this.employeeService.deleteEmployee(employee.id).subscribe({
      next: value => {
        this.loadEmployees();
      }
    })
  }

  addEmployee(): void {
    const dialogRef = this.dialog.open(EmployeeFormComponent, {
      width: '400px',
      data: this.departmentName
    });
    dialogRef.afterClosed().subscribe(isCreated => {
      if (isCreated) {
        this.loadEmployees();
      }
    });
  }

  onSearchEmployee(keyword: string): void {
    if (keyword.trim()) {
      this.employeeService.findEmployeesByFullName(keyword).subscribe({
        next: value => {
          this.searchEmployees.set(value);
        }
      });
    } else {
      this.searchEmployees.set(null);
      this.loadEmployees();
    }
  }
}
