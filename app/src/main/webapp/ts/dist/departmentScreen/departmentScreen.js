"use strict";
// Department Screen Dialog Management
var DepartmentScreenNamespace;
(function (DepartmentScreenNamespace) {
    const openDialogButtonIds = Array.of("newdepartment");
    openDialogButtonIds.forEach((dialogId) => {
        const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`);
        const dialogElement = document.getElementById(`${dialogId}-dialog`);
        if (openDialogButton) {
            openDialogButton.addEventListener("click", () => {
                dialogElement.showModal();
            });
        }
        else {
            console.log("OpenDialogButton not found: " + dialogId);
        }
    });
    const dialogrelatedId = Array.of("newdepartment", "removedepartment", "newlycreateddepartment", "members");
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
            console.log("HTML Element not found for dialog: " + dialogId);
        }
    });
})(DepartmentScreenNamespace || (DepartmentScreenNamespace = {}));
// Function to handle employee assignment with refresh
function assignEmployee(button) {
    const form = button.closest("form");
    if (!form) {
        console.error("Form not found");
        return;
    }
    const formData = new FormData(form);
    const params = new URLSearchParams();
    params.append("action", formData.get("action"));
    params.append("departmentId", formData.get("departmentId"));
    params.append("employeeId", formData.get("employeeId"));
    fetch("/app/departmentServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: params.toString(),
    })
        .then((response) => {
        if (!response.ok) {
            throw new Error("Assignment failed");
        }
        return response.text();
    })
        .then((responseText) => {
        console.log("Employee assigned successfully, refreshing list...");
        // Trigger the refresh event on body to trigger HTMX
        if (window.htmx) {
            window.htmx.trigger("#dept-list", "RefreshDepartmentList");
        }
        else {
            // Fallback: manually refresh the department list
            const deptList = document.getElementById("dept-list");
            if (deptList) {
                fetch("/app/departmentServlet?action=getdepartmentfulllist")
                    .then((res) => res.text())
                    .then((html) => {
                    deptList.innerHTML = html;
                });
            }
        }
    })
        .catch((error) => {
        console.error("Assignment error:", error);
        alert("Error assigning employee");
    });
}
// Expose function globally
window.assignEmployee = assignEmployee;
