export function modifyEmployee(id) {
    const modifyEmployeeDialog = document.getElementById("modifyemployee-dialog");
    const modifyEmployeeSubmitButton = document.getElementById("modifyEmployee-form-submitBtn");
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
    if (typeof (modifyEmployeeSubmitButton.onclick) != "function") {
        modifyEmployeeSubmitButton.addEventListener("click", async () => modifyEmployeeRequest(id, modifyEmployeeSubmitButton, modifyEmployeeDialog));
    }
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
function getEmployee(employeeId) {
    const rowDiv = document.querySelector(`[data-id="${employeeId}"]`);
    if (!employeeId) {
        console.log("Employee Id at getEmployee() not found");
        console.log("employee id :" + employeeId);
        return null;
    }
    if (!rowDiv) {
        console.error("Div not found at getEmployee()");
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
//Implementation not ready
async function modifyEmployeeRequest(id, modifyEmployeeSubmitButton, modifyEmployeeDialog) {
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
    try {
        const response = await fetch("", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: JSON.stringify("")
        });
    }
    catch (error) {
        console.error(error);
    }
}
//expose function globabally
;
window.modifyEmployee = modifyEmployee;
