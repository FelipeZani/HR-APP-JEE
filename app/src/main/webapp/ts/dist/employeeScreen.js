const openDialogButtonIds = Array.of("searchemployee", "newemployee");
openDialogButtonIds.forEach((dialogId) => {
    const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`);
    const dialogElement = document.getElementById(`${dialogId}-dialog`);
    if (openDialogButton) {
        openDialogButton.addEventListener("click", () => {
            dialogElement.showModal();
        });
    }
    else {
        console.log("OpenDialogButton not found: " + openDialogButton);
    }
});
const DIALOGRELATEDID = Object.freeze({
    researchEmployee: "searchemployee",
    newEmployee: "newemployee",
    modifyEmployee: "modifyemployee",
    removeEmployee: "removeemployee"
});
const dialogrelatedId = Array.of("searchemployee", "newemployee", "modifyemployee", "removeemployee");
dialogrelatedId.forEach((dialogId) => {
    const closeDialogButton = document.getElementById(`${dialogId}-closeButton`);
    const dialogElement = document.getElementById(`${dialogId}-dialog`);
    const formDialog = document.getElementById(`${dialogId}-form`);
    if (dialogElement && formDialog && closeDialogButton) {
        dialogElement.addEventListener("click", () => {
            dialogElement.close();
        });
        closeDialogButton.addEventListener("click", () => {
            dialogElement.close();
        });
        formDialog.addEventListener("click", (e) => {
            e.stopPropagation();
        });
    }
    else {
        console.log("HTML Element not found : " + dialogElement + " " + formDialog + " " + closeDialogButton);
    }
});
function getEmployee(employeeId) {
    const rowDiv = document.querySelector(`[data-id="${employeeId}"]`);
    if (!employeeId) {
        console.log("Employee Id in getEmployee not found");
        console.log("employee id :" + employeeId);
        return null;
    }
    if (!rowDiv) {
        console.error("Div not found");
        console.log("row : " + rowDiv);
        return null;
    }
    const selectedEmployee = {
        id: employeeId,
        name: rowDiv.querySelector('.name').textContent,
        post: rowDiv.querySelector('.post').textContent,
        department: rowDiv.querySelector('.department').textContent,
        rank: rowDiv.querySelector('.rank').textContent
    };
    return selectedEmployee;
}
function setPlaceHolder(id) {
    if (id === "") {
        console.log("Id can't be empty");
        return;
    }
    const employee = getEmployee(id);
    if (!employee) {
        console.log("Employee not found");
        return;
    }
    const inputName = document.getElementById("name-input");
    const inputPost = document.getElementById("post-input");
    const inputDepartment = document.getElementById("department-input");
    const inputRank = document.getElementById("rank-input");
    if (!inputName || !inputPost || !inputDepartment || !inputRank) {
        console.log("Input field not valid");
        return;
    }
    inputName.placeholder = employee.name;
    inputPost.placeholder = employee.post;
    inputDepartment.placeholder = employee.department;
    inputRank.placeholder = employee.rank;
}
function modifyEmployee(id) {
    if (id === "") {
        console.log("Id can't be an empty string at modifyEmployee()");
        return;
    }
    const modifyEmployeeDialog = document.getElementById("modifyemployee-dialog");
    if (!modifyEmployeeDialog) {
        console.log("modifyemployeeDialog not found");
        return;
    }
    setPlaceHolder(id);
    modifyEmployeeDialog.showModal();
    //submit
}
function removeEmployee(id) {
    if (id === "") {
        console.log("Id can't be an empty string at removeEmployee()");
        return;
    }
    const removeEmployeeDialog = document.getElementById("removeemployee-dialog");
    if (!removeEmployeeDialog) {
        console.log("removeemployeeDialog not found");
        return;
    }
    removeEmployeeDialog.showModal();
}
//expose function globabally
;
window.modifyEmployee = modifyEmployee;
;
window.removeEmployee = removeEmployee;
export {};
