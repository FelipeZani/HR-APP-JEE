
import { EmployeeDataType } from "../types";

export function modifyEmployee(id: string) {

    const modifyEmployeeDialog = document.getElementById("modifyemployee-dialog") as HTMLDialogElement;
    const modifyEmployeeSubmitButton = document.getElementById("modifyEmployee-form-submitBtn") as HTMLButtonElement;

    if (id === "") {
        console.log("Id can't be an empty string at modifyEmployee()");
        return;
    }

    if (!modifyEmployeeSubmitButton) {
        console.log("ModifyEmployeeSubmitBtn not found");
        return;
    }

    if (!modifyEmployeeDialog) {
        console.log("modifyemployeeDialog not found ");
        return;
    }

    setPlaceHolder(id);
    modifyEmployeeDialog.showModal();




    if (typeof (modifyEmployeeSubmitButton!!.onclick) != "function") {
        modifyEmployeeSubmitButton!!.addEventListener("click", async () => modifyEmployeeRequest(id, modifyEmployeeSubmitButton, modifyEmployeeDialog));

    }
}

function setPlaceHolder(id: string) {
    if (id === "") {
        console.log("Id can't be empty");
        return;
    }
    const employee: EmployeeDataType | null = getEmployee(id);

    if (!employee) {
        console.log("Employee not found");
        return;
    }

    const inputName = document.getElementById("name-input") as HTMLInputElement;
    const inputPost = document.getElementById("post-input") as HTMLInputElement;
    const inputDepartment = document.getElementById("department-input") as HTMLInputElement;
    const inputRank = document.getElementById("rank-input") as HTMLInputElement;

    if (!inputName || !inputPost || !inputDepartment || !inputRank) {
        console.log("Input field not valid");
        return;
    }
    inputName.placeholder = employee.name;
    inputPost.placeholder = employee.post;
    inputDepartment.placeholder = employee.department;
    inputRank.placeholder = employee.rank;


}


function getEmployee(employeeId: string): EmployeeDataType | null {

    const rowDiv = document.querySelector(`[data-id="${employeeId}"]`) as HTMLDivElement;

    if (!employeeId) {

        console.log("Employee Id at getEmployee() not found");
        console.log("employee id :" + employeeId)
        return null;
    }

    if (!rowDiv) {

        console.error("Div not found at getEmployee()");
        console.log("row : " + rowDiv)
        return null;

    }

    const selectedEmployee: EmployeeDataType = {
        id: employeeId,
        name: rowDiv.querySelector('.name')!!.textContent,
        post: rowDiv.querySelector('.post')!!.textContent,
        department: rowDiv.querySelector('.department')!!.textContent,
        rank: rowDiv.querySelector('.rank')!!.textContent
    }
    return selectedEmployee;


}


//Implementation not ready
async function modifyEmployeeRequest(id: string, modifyEmployeeSubmitButton: HTMLButtonElement, modifyEmployeeDialog: HTMLDialogElement) {


    const form = document.getElementById("modifyemployee-form") as HTMLFormElement;


    if (id === "") {
        console.log("Id can't be an empty string at modifyEmployeeRequest()");
        return;
    }

    if (!modifyEmployeeSubmitButton) {
        console.log("ModifyEmployeeSubmitBtn not found at modifyEmployeeRequest()");
        return;
    }

    if (!modifyEmployeeDialog) {
        console.log("modifyemployeeDialog not found at modifyEmployeeRequest()");
        return;
    }
    if (!form) {
        console.log("form not found at modifyEmployeeRequest()");
        return;
    }

    const formData = new FormData(form);

    // const modifiedEmployee: any = {
    //     action: formData.get("action"),
    //     name: `${formData.get("name") as string} ${formData.get("lastname") as string}`,
    //     post: formData.get("post") as string,
    //     department: formData.get("department") as string,
    //     rank: formData.get("rank") as string,
    //     newPassword : form.get("newpassword") as string

    // }

    // console.log(modifiedEmployee);

    // try {
    //     const response = await fetch("", {
    //         method: "PATCH",
    //         headers: {
    //             "Content-Type": "application/x-www-form-urlencoded",
    //         },
    //         body: JSON.stringify(modifiedEmployee)
    //     });


    // } catch (error) {
    //     console.error(error);
    // }




}


//expose function globabally
; (window as any).modifyEmployee = modifyEmployee;





