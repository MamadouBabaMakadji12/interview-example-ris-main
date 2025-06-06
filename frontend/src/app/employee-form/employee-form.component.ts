import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CreateEmployee } from '../models/create.employee.dto';
import { EmployeeService } from '../services/employee/employee.service';

@Component({
  selector: 'app-employee-form',
  standalone: false,
  templateUrl: './employee-form.component.html',
  styleUrl: './employee-form.component.scss'
})
export class EmployeeFormComponent implements OnInit {
  private dialogRef = inject(MatDialogRef<EmployeeFormComponent>);
  private employeeService = inject(EmployeeService);

  employeeForm!: FormGroup;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.employeeForm = this.fb.group({
      fullName: ['', Validators.required],
      address: ['', Validators.required],
      phoneNumber: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      departmentName: [this.data, Validators.required]
    });
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onSubmit() {
    if (this.employeeForm.valid) {
      const newEmployee: CreateEmployee = this.employeeForm.value;
      this.employeeService.createEmployee(newEmployee).subscribe({
        next: (res) => {
          this.employeeForm.reset();
          this.dialogRef.close(true);
        }
      });
    }
  }
}
