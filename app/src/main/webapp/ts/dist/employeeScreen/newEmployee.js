import { modifyEmployee } from './modifyEmployee.js';
import { removeEmployee } from './removeEmployee.js';
const submitButton = document.getElementById("newemployee-form-submitBtn");
submitButton.addEventListener("click", () => {
    sendData();
});
async function sendData() {
    const form = document.getElementById("newemployee-form");
    if (!form) {
        console.log("newemployee form not found");
        return;
    }
    if (!form.checkValidity()) {
        console.log("Form is not valid");
        return;
    }
    const formData = new FormData(form);
    const newEmployee = {
        action: formData.get("action"),
        name: `${formData.get("name")} ${formData.get("lastname")}`,
        post: formData.get("post"),
        department: formData.get("department"),
        rank: formData.get("rank")
    };
    const newEmployeeURL = new URLSearchParams(newEmployee);
    console.log(newEmployeeURL.toString());
    try {
        const formData = new FormData(form);
        if (formData) {
            const response = await fetch("http://localhost:8080/app/employeeServlet", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: newEmployeeURL.toString(),
            });
            if (response.ok) {
                const responseData = await response.json();
                const newlyCreatedEmployee = {
                    id: responseData.id,
                    name: responseData.name,
                    post: responseData.post,
                    department: responseData.department,
                    rank: responseData.rank,
                    username: responseData.username,
                    password: responseData.password,
                };
                showCreatedEmployeeModal(newlyCreatedEmployee);
                appendEmployeeToHome(newlyCreatedEmployee);
            }
            else {
                console.log(response.statusText);
            }
        }
        else {
            console.error("Problem with formData");
        }
    }
    catch (e) {
        console.error(e);
    }
}
function showCreatedEmployeeModal(emp) {
    const dialog = document.getElementById("newemployee-dialog");
    const dialog2 = document.getElementById("newlycreatedemployee-dialog");
    if (!dialog || !dialog2) {
        console.log("Dialog newemployee : ", dialog);
        console.log("Dialog newlycreated employee : ", dialog2);
        return;
    }
    const usernameP = document.getElementById("newlycreatedemployee-username");
    const passwordP = document.getElementById("newlycreatedemployee-password");
    if (usernameP)
        usernameP.textContent = emp.username;
    if (passwordP)
        passwordP.textContent = emp.password;
    dialog.close();
    dialog2.showModal();
}
function appendEmployeeToHome(emp) {
    const bodyTable = document.getElementById("bodytable");
    if (!bodyTable) {
        console.log("BodyTable id not found");
        return;
    }
    ;
    const rowDiv = document.createElement("div");
    rowDiv.className = "row";
    rowDiv.dataset.id = `${emp.id}`;
    const nameDiv = document.createElement("div");
    nameDiv.classList.add("name");
    nameDiv.textContent = emp.name;
    const postDiv = document.createElement("div");
    postDiv.classList.add("post");
    postDiv.textContent = emp.post;
    const departmentDiv = document.createElement("div");
    departmentDiv.classList.add("department");
    departmentDiv.textContent = emp.department;
    const rankDiv = document.createElement("div");
    rankDiv.classList.add("rank");
    rankDiv.textContent = emp.rank;
    const actionDiv = document.createElement("div");
    const modifyEmployeeButton = document.createElement("button");
    const deleteEmployeeButton = document.createElement("button");
    modifyEmployeeButton.textContent = "modify";
    deleteEmployeeButton.textContent = "remove";
    modifyEmployeeButton.classList.add("modifyemployee-opendialogbutton");
    modifyEmployeeButton.onclick = () => modifyEmployee(emp.id.toString());
    deleteEmployeeButton.onclick = () => removeEmployee(emp.id.toString());
    actionDiv.appendChild(modifyEmployeeButton);
    actionDiv.appendChild(deleteEmployeeButton);
    rowDiv.appendChild(nameDiv);
    rowDiv.appendChild(postDiv);
    rowDiv.appendChild(departmentDiv);
    rowDiv.appendChild(rankDiv);
    rowDiv.appendChild(actionDiv);
    bodyTable.appendChild(rowDiv);
}
