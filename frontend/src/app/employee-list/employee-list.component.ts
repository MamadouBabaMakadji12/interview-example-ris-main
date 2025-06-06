import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Employee } from '../models/employee.model';

@Component({
  selector: 'employee-list',
  standalone: false,
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.scss'
})
export class EmployeeListComponent {
  @Input() employees!: Array<Employee> | null;
  @Input() canDelete: boolean = true;
  @Output() onClickEmployee: EventEmitter<Employee> = new EventEmitter<Employee>();
  @Output() onDeleteEmployee: EventEmitter<Employee> = new EventEmitter<Employee>();

  onClick(employee: Employee): void {
    this.onClickEmployee.emit(employee);
  }

  delete(employee: Employee): void {
    this.onDeleteEmployee.emit(employee);
  }
}
