import { Department } from "./department.model";

export interface Employee {
    id: number;
    fullName: string;
    address: string;
    phoneNumber: string;
    email: string;
    department: Department;
}