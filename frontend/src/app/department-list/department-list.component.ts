import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DepartmentFormComponent } from '../department-form/department-form.component';
import { DepartmentDto } from '../models/department.dto';
import { Employee } from '../models/employee.model';
import { DepartmentService } from '../services/department/department.service';
import { EmployeeService } from '../services/employee/employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'department-list',
  standalone: false,
  templateUrl: './department-list.component.html',
  styleUrl: './department-list.component.scss'
})
export class DepartmentListComponent implements OnInit {
  private router = inject(Router);
  private readonly departmentService = inject(DepartmentService);
  private employeeService = inject(EmployeeService);
  departments: WritableSignal<Array<DepartmentDto> | null> = signal(null);
  private dialog = inject(MatDialog);
  searchEmployees: WritableSignal<Array<Employee> | null> = signal(null);

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.departmentService.getDepartments().subscribe({
      next: value => {
        this.departments.set(value)
      },
    })
  }

  delete(departmentId: number): void {
    this.departmentService.deleteDepartment(departmentId).subscribe({
      next: value => {
        // ToDo: add toastr
        this.loadDepartments();
      }
    });
  }

  addDepartment(): void {
    const dialogRef = this.dialog.open(DepartmentFormComponent, {
      width: '400px'
    });
    dialogRef.afterClosed().subscribe(isCreated => {
      if (isCreated) {
        this.loadDepartments();
      }
    });
  }

  onSearch(keyword: string): void {
    if (keyword.trim()) {
      this.employeeService.findEmployeesByFullName(keyword).subscribe({
        next: value => {
          this.searchEmployees.set(value);
        }
      });
    } else {
      this.searchEmployees.set(null);
    }
  }

  openDepartmentView(employee: Employee): void {
    this.router.navigateByUrl(`/department-view/${employee?.department?.name ?? 'Unassigned'}`);
  }

}
