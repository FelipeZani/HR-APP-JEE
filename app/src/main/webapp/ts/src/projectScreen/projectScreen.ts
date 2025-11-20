// Project Screen Dialog Management

namespace ProjectScreenNamespace {
    const openDialogButtonIds: ReadonlyArray<string> = Array.of("newproject");

    openDialogButtonIds.forEach((dialogId) => {
        const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`) as HTMLButtonElement;
        const dialogElement = document.getElementById(`${dialogId}-dialog`) as HTMLDialogElement;

        if (openDialogButton) {
            openDialogButton.addEventListener("click", () => {
                dialogElement.showModal();
            });
        } else {
            console.debug("OpenDialogButton not found: " + dialogId);
        }
    });

    const dialogrelatedId: ReadonlyArray<string> = Array.of("newproject", "newlycreatdproject", "modifyproject", "removeproject", "members");

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
        } else if (dialogId === "members") {
            // Handle members dialog which doesn't have a form
            if (dialogElement && closeDialogButton) {
                dialogElement.addEventListener("click", () => {
                    dialogElement.close();
                });

                closeDialogButton.addEventListener("click", () => {
                    dialogElement.close();
                });
            }
        } else {
            console.debug("HTML Element not found for dialog: " + dialogId);
        }
    });
}

// Function to handle employee assignment with refresh
function assignEmployeeToProject(button: HTMLButtonElement) {
    const form = button.closest("form") as HTMLFormElement;
    if (!form) {
        console.error("Form not found");
        return;
    }

    const formData = new FormData(form);
    const employeeSelect = form.querySelector("select[name='employeeId']") as HTMLSelectElement;
    
    if (!employeeSelect || !employeeSelect.value) {
        alert("Please select an employee first");
        return;
    }

    const params = new URLSearchParams();
    params.append("action", formData.get("action") as string);
    params.append("projectId", formData.get("projectId") as string);
    params.append("employeeId", formData.get("employeeId") as string);

    fetch("/app/projectServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: params.toString(),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Assignment failed with status " + response.status);
            }
            return response.text();
        })
        .then((responseText) => {
            if (responseText === "ALREADY_ASSIGNED") {
                alert("This employee is already assigned to this project");
                return;
            }
            
            if (responseText === "OK") {
                console.debug("Employee assigned successfully to project");
                // Reset the select
                if (employeeSelect) {
                    employeeSelect.value = "";
                }
                // Trigger the refresh event
                if ((window as any).htmx) {
                    (window as any).htmx.trigger("#project-list", "RefreshProjectList");
                } else {
                    // Fallback: manually refresh the project list
                    const projectList = document.getElementById("project-list");
                    if (projectList) {
                        fetch("/app/projectServlet?action=getprojectlist")
                            .then((res) => res.text())
                            .then((html) => {
                                projectList.innerHTML = html;
                            });
                    }
                }
            }
        })
        .catch((error) => {
            console.error("Error assigning employee:", error);
            alert("Error: " + error);
        });
}

// Expose function globally
(window as any).assignEmployeeToProject = assignEmployeeToProject;
