import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Department } from '../models/department.model';
import { DepartmentService } from '../services/department/department.service';

@Component({
  selector: 'app-department-form',
  standalone: false,
  templateUrl: './department-form.component.html',
  styleUrl: './department-form.component.scss'
})
export class DepartmentFormComponent implements OnInit {
  private dialogRef = inject(MatDialogRef<DepartmentFormComponent>);
  private departmentService = inject(DepartmentService);

  departmentForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.departmentForm = this.fb.group({
      name: ['', [Validators.required]]
    });
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onSubmit() {
    if (this.departmentForm.valid) {
      const department: Department = this.departmentForm.value;
      this.departmentService.createDepartment(department).subscribe({
        next: (value) => {
          this.dialogRef.close(true);
        },
      });
    }
  }
}
