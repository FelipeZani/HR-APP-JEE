// Department Screen Dialog Management

namespace DepartmentScreenNamespace {
    const openDialogButtonIds: ReadonlyArray<string> = Array.of("newdepartment");

    openDialogButtonIds.forEach((dialogId) => {
        const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`) as HTMLButtonElement;
        const dialogElement = document.getElementById(`${dialogId}-dialog`) as HTMLDialogElement;

        if (openDialogButton) {
            openDialogButton.addEventListener("click", () => {
                dialogElement.showModal();
            });
        } else {
            console.log("OpenDialogButton not found: " + dialogId);
        }
    });

    const dialogrelatedId: ReadonlyArray<string> = Array.of("newdepartment", "removedepartment", "newlycreateddepartment", "members");

    dialogrelatedId.forEach((dialogId) => {
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
            console.log("HTML Element not found for dialog: " + dialogId);
        }
    });
}

// Function to handle employee assignment with refresh
function assignEmployee(button: HTMLButtonElement) {
    const form = button.closest("form") as HTMLFormElement;
    if (!form) {
        console.error("Form not found");
        return;
    }

    const formData = new FormData(form);
    const params = new URLSearchParams();
    params.append("action", formData.get("action") as string);
    params.append("departmentId", formData.get("departmentId") as string);
    params.append("employeeId", formData.get("employeeId") as string);

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
            if ((window as any).htmx) {
                (window as any).htmx.trigger("#dept-list", "RefreshDepartmentList");
            } else {
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
(window as any).assignEmployee = assignEmployee;
