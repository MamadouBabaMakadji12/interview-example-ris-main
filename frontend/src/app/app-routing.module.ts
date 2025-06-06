import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DepartmentViewComponent } from './department-view/department-view.component';
import { EmployeeViewComponent } from './employee-view/employee-view.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'department-view/:name', component: DepartmentViewComponent },
  { path: 'employee-view/:id', component: EmployeeViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
