export function removeDepartment(id: string) {
    const removeDepartmentDialog = document.getElementById("removedepartment-dialog") as HTMLDialogElement;
    const removeDepartmentSubmitButton = document.getElementById("removedepartment-submitButton") as HTMLButtonElement;

    if (id === "") {
        console.log("Id can't be an empty string at removeDepartment()");
        return;
    }

    if (!removeDepartmentDialog) {
        console.log("removedepartmentDialog not found");
        return;
    }

    if (!removeDepartmentSubmitButton) {
        console.log("removeDepartmentSubmitButton was not found");
        return;
    }

    removeDepartmentDialog.showModal();

    if (typeof (removeDepartmentSubmitButton!!.onclick) != "function") {
        removeDepartmentSubmitButton!!.addEventListener("click", async () => removeDepartmentRequest(id, removeDepartmentSubmitButton, removeDepartmentDialog));
    }
}

async function removeDepartmentRequest(id: string, removeDepartmentSubmitButton: HTMLElement, removeDepartmentDialog: HTMLDialogElement) {
    const urlSearchParams = new URLSearchParams({
        action: "deletdepartment",
        departmentId: id
    });

    try {
        const response = await fetch("/app/departmentServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: urlSearchParams.toString()
        });

        if (!response.ok) {
            throw new Error("RemoveDepartmentRequest failed");
        }

        removeDepartmentDialog.close();

        // Trigger HTMX refresh event
        if ((window as any).htmx) {
            (window as any).htmx.trigger("#dept-list", "RefreshDepartmentList");
        }

    } catch (error) {
        console.error(error);
        alert("Error removing department");
    }
}

// Expose function globally
(window as any).removeDepartment = removeDepartment;
