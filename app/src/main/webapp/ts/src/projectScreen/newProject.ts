async function sendData() {
    const form = document.getElementById("newproject-form") as HTMLFormElement;

    if (!form) {
        console.debug("newproject form not found");
        return;
    }

    if (!form.checkValidity()) {
        console.debug("Form is not valid");
        return;
    }

    const formData = new FormData(form);

    const newProject: any = {
        action: formData.get("action"),
        label: formData.get("label") as string,
        status: formData.get("status") as string,
        deadLine: formData.get("deadLine") as string,
        managerId: formData.get("managerId") as string
    }
    
    const newProjectURL = new URLSearchParams(newProject);
    console.debug(newProjectURL.toString());

    try {
        const response = await fetch("/app/projectServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: newProjectURL.toString(),
        });

        if (response.ok) {
            const responseText = await response.text();
            if (responseText === "OK") {
                showCreatedProjectModal();
                // Trigger HTMX refresh event
                (window as any).htmx.trigger("#project-list", "RefreshProjectList");
            }
        } else {
            console.debug(response.statusText);
        }
    } catch (e) {
        console.error(e);
    }
}

function showCreatedProjectModal() {
    const dialog = document.getElementById("newproject-dialog") as HTMLDialogElement;
    const dialog2 = document.getElementById("newlycreatdproject-dialog") as HTMLDialogElement;

    if (!dialog || !dialog2) {
        console.debug("Dialog newproject :", dialog);
        console.debug("Dialog newlycreatdproject :", dialog2);
        return;
    }

    dialog.close();
    dialog2.showModal();

    setTimeout(() => {
        dialog2.close();
    }, 2000);
}

const submitButton = document.getElementById("newproject-form-submitBtn") as HTMLButtonElement;

if (submitButton) {
    submitButton.addEventListener("click", () => {
        sendData();
    });
}

// Expose function globally
(window as any).showCreatedProjectModal = showCreatedProjectModal;
