import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { Employee } from '../models/employee.model';
import { EmployeeService } from '../services/employee/employee.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-employee-view',
  standalone: false,
  templateUrl: './employee-view.component.html',
  styleUrl: './employee-view.component.scss'
})
export class EmployeeViewComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private employeeService = inject(EmployeeService);
  employee: WritableSignal<Employee | null> = signal(null);

  ngOnInit(): void {
    this.loadEmployees(Number(this.route.snapshot.paramMap.get('id')) ?? null);
  }

  loadEmployees(id: number): void {
    this.employeeService.findEmployeesById(id).subscribe({
      next: value => {
        this.employee.set(value);
      }
    });
  }
}
