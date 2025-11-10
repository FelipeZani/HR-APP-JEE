export interface EmployeeDataType{
    id: string | null,
    name: string,
    post: string,
    department: string,
    rank: string
}


export interface NewlyCreatedEmployeeType extends EmployeeDataType {
    password: string,
    username: string
}