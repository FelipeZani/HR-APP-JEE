
import { EmployeeDataType } from "../types";



export function modifyEmployee(id: string) {

    const modifyEmployeeDialog = document.getElementById("modifyemployee-dialog") as HTMLDialogElement;
    const modifyEmployeeSubmitButton = document.getElementById("modifyemployee-form-submitBtn") as HTMLButtonElement;

    if (id === "" || !id.match(RegExp("[0-9]+"))) {
        console.log("Id format is not allowed, at modifyEmployee()");
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
        modifyEmployeeSubmitButton!!.onclick = async () => modifyEmployeeRequest(id, modifyEmployeeSubmitButton, modifyEmployeeDialog);

    }
}

function setPlaceHolder(id: string) {
    if (id === "") {
        console.log("Id can't be empty");
        return;
    }


    const nameInput = document.getElementById("name-input") as HTMLInputElement;
    const lastnameInput = document.getElementById("lastname-input") as HTMLInputElement;
    const passwordInput = document.getElementById("password-input") as HTMLInputElement;
    const rankSelect = document.getElementById("rank-select") as HTMLSelectElement;
    const postSelect = document.getElementById("post-select") as HTMLSelectElement;

    const employeeData = getEmployee(id);

    if (!nameInput || !passwordInput || !lastnameInput || !rankSelect || !postSelect) {
        console.log("Input field not valid at setplaceHolder()");
        return;
    }


    nameInput.placeholder = employeeData!!.name;
    lastnameInput.placeholder = employeeData!!.lastname;
    rankSelect.value = employeeData!!.rank.toLowerCase();



}

//It s assumed that in order to modify an employee, the employee exist in the front end, otherwise the modify employee open dialog button wouldn't be recheable
function getEmployee(employeeId: string) {

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

    const fullname: string[] = rowDiv.querySelector('.name')!!.textContent.split(" ");

    const selectedEmployee = {
        id: employeeId,
        name: fullname[0],
        lastname: fullname[1],
        rank: rowDiv.querySelector('.rank')!!.textContent,
        post : rowDiv.querySelector('.post')!!.textContent
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
    if (!form.checkValidity()) {
        console.log("Form to modify employee not valid");
        return;
    }

    const formData = new FormData(form);
    const modifiedEmployee: any = {

        action: formData.get("action") as string,
        id: id as string,
        name: formData.get("modifyemployee-name-input") as string,
        lastname: formData.get("modifyemployee-lastname-input") as string,
        rank: formData.get("rank") as string,
        newPassword: formData.get("modifyemployee-password-input") as string

    }

    if (!checkFormValuesChanged(modifiedEmployee)) {

        console.log("Form values didn't change");
        return;
    }

    const searchParams = new URLSearchParams(modifiedEmployee);

    try {
        const response = await fetch("http://localhost:8080/app/employeeServlet?" + searchParams, {
            method: "PATCH"

        });

        if (response.ok) {
            const dataResponse = await response.json();

            const updatedRowDiv = document.querySelector(`[data-id='${dataResponse.id}']`);

            const nameCol = updatedRowDiv?.querySelector('.name');
            const rankCol = updatedRowDiv?.querySelector('.rank');
            const postCol = updatedRowDiv?.querySelector('.post');

            nameCol!!.textContent = dataResponse.name;
            rankCol!!.textContent = dataResponse.rank;
            postCol!!.textContent = dataResponse.post;
        }
    } catch (error) {
        console.error(error);
    }




}


//expose function globabally
; (window as any).modifyEmployee = modifyEmployee;





function checkFormValuesChanged(employeeData: any) {
    let count = 0;
    const expectedNotNullEmementsNb = 4;
    Object.values(employeeData).forEach((value) => {
        if (typeof value != 'undefined' && value) {
            count++;
        }

    });

    console.log(count);

    return count >= expectedNotNullEmementsNb;
}

