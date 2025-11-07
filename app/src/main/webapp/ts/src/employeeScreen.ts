import { EmployeeDataType } from "./types"

interface DialogTypeEmployeeScreen {
    researchEmployee: string
    newEmployee: string
    modifyEmployee: string
}

const openDialogButtonIds:ReadonlyArray<string> = Array.of("searchemployee", "newemployee");

openDialogButtonIds.forEach((dialogId) => {
    const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`) as HTMLButtonElement;
    const dialogElement = document.getElementById(`${dialogId}-dialog`) as HTMLDialogElement;

    if (openDialogButton) {
        openDialogButton.addEventListener("click", () => {
            dialogElement.showModal();

        });
    } else {
        console.log("OpenDialogButton not found: " + openDialogButton);
    }

});

const DIALOGRELATEDID: Readonly<DialogTypeEmployeeScreen> = Object.freeze({
    researchEmployee: "searchemployee",
    newEmployee: "newemployee",
    modifyEmployee: "modifyemployee",
    removeEmployee: "removeemployee"

});
const dialogrelatedId : ReadonlyArray<string> = Array.of("searchemployee", "newemployee", "modifyemployee","removeemployee");


dialogrelatedId.forEach((dialogId)=>{
    const closeDialogButton = document.getElementById(`${dialogId}-closeButton`) as HTMLButtonElement;
    const dialogElement = document.getElementById(`${dialogId}-dialog`) as HTMLDialogElement;
    const formDialog = document.getElementById(`${dialogId}-form`) as HTMLFormElement;


        if (dialogElement && formDialog && closeDialogButton) {

        dialogElement.addEventListener("click", () => {
            dialogElement.close();

        });

        closeDialogButton.addEventListener("click", () => {
            dialogElement.close();

        });


        formDialog.addEventListener("click", (e: Event) => {
            e.stopPropagation();
        });
    } else {
        console.log("HTML Element not found : " + dialogElement + " " + formDialog + " " + closeDialogButton);
    }
});





function getEmployee(employeeId: string): EmployeeDataType | null {

    const rowDiv = document.querySelector(`[data-id="${employeeId}"]`) as HTMLDivElement;

    if (!employeeId) {

        console.log("Employee Id in getEmployee not found");
        console.log("employee id :" + employeeId)
        return null;
    }
    if (!rowDiv) {

        console.error("Div not found");
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

function modifyEmployee(id: string) {
    if (id === "") {
        console.log("Id can't be an empty string at modifyEmployee()");
        return;
    }



    const modifyEmployeeDialog = document.getElementById("modifyemployee-dialog") as HTMLDialogElement;

    if (!modifyEmployeeDialog) {
        console.log("modifyemployeeDialog not found");
        return;
    }

    setPlaceHolder(id);
    modifyEmployeeDialog.showModal();



    //submit
}

function removeEmployee(id: string) {
    if (id === "") {
        console.log("Id can't be an empty string at removeEmployee()");
        return;
    }

    const removeEmployeeDialog = document.getElementById("removeemployee-dialog") as HTMLDialogElement;

    if (!removeEmployeeDialog) {
        console.log("removeemployeeDialog not found");
        return;
    }

    removeEmployeeDialog.showModal();

}

//expose function globabally
; (window as any).modifyEmployee = modifyEmployee;
; (window as any).removeEmployee = removeEmployee;





