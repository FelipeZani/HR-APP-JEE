//import { modifyDepartment } from './modifyDepartment.js';
//import { removeDepartment } from './removeDepartment.js'
const submitButton = document.getElementById("newdepartment-form-submitBtn");
if (submitButton) {
    submitButton.addEventListener("click", () => {
        sendData();
    });
}
async function sendData() {
    const form = document.getElementById("newdepartment-form");
    if (!form) {
        console.log("newdepartment form not found");
        return;
    }
    if (!form.checkValidity()) {
        console.log("Form is not valid");
        return;
    }
    const formData = new FormData(form);
    const newDepartment = {
        action: formData.get("action"),
        name: formData.get("name"),
        managerId: formData.get("managerId")
    };
    const newDepartmentURL = new URLSearchParams(newDepartment);
    console.log(newDepartmentURL.toString());
    try {
        const response = await fetch("http://localhost:8080/app/departmentServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: newDepartmentURL.toString(),
        });
        if (response.ok) {
            const responseText = await response.text();
            if (responseText === "OK") {
                showCreatedDepartmentModal();
                // Trigger HTMX refresh event
                window.htmx.trigger("#dept-list", "RefreshDepartmentList");
            }
        }
        else {
            console.log(response.statusText);
        }
    }
    catch (e) {
        console.error(e);
    }
}
function showCreatedDepartmentModal() {
    const dialog = document.getElementById("newdepartment-dialog");
    const dialog2 = document.getElementById("newlycreateddepartment-dialog");
    if (!dialog || !dialog2) {
        console.log("Dialog newdepartment :", dialog);
        console.log("Dialog newlycreated department :", dialog2);
        return;
    }
    dialog.close();
    dialog2.showModal();
    setTimeout(() => {
        dialog2.close();
    }, 2000);
}
// Expose function globally
window.appendDepartmentToHome = appendDepartmentToHome;
function appendDepartmentToHome(dept) {
    const departmentList = document.getElementById("dept-list");
    if (!departmentList) {
        console.log("Department list container not found");
        return;
    }
    const departmentRow = document.createElement("div");
    departmentRow.className = "department-row";
    departmentRow.dataset.id = dept.id;
    departmentRow.innerHTML = `
        <div><strong>${dept.name}</strong> (id: ${dept.id})</div>
        <div>Manager: ${dept.manager || '--'}</div>
        <div style="margin-top: 10px;">
            <button onclick="removeDepartment('${dept.id}')">remove</button>
        </div>
    `;
    departmentList.appendChild(departmentRow);
}
export {};
