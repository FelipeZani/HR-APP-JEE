export interface EmployeeDataType{
    id: string,
    name: string,
    post: string,
    department: string,
    rank: string
}


export interface NewlyCreatedEmployeeType extends EmployeeDataType {
    password: string,
    username: string
}

export interface DepartmentDataType {
    id: string,
    name: string,
    manager: string
}

export interface NewlyCreatedDepartmentType extends DepartmentDataType {
    createdAt?: string
}
