"use strict";
async function sendData() {
    const form = document.getElementById("newproject-form");
    if (!form) {
        console.debug("newproject form not found");
        return;
    }
    if (!form.checkValidity()) {
        console.debug("Form is not valid");
        return;
    }
    const formData = new FormData(form);
    const newProject = {
        action: formData.get("action"),
        label: formData.get("label"),
        status: formData.get("status"),
        deadLine: formData.get("deadLine"),
        managerId: formData.get("managerId")
    };
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
                window.htmx.trigger("#project-list", "RefreshProjectList");
            }
        }
        else {
            console.debug(response.statusText);
        }
    }
    catch (e) {
        console.error(e);
    }
}
function showCreatedProjectModal() {
    const dialog = document.getElementById("newproject-dialog");
    const dialog2 = document.getElementById("newlycreatdproject-dialog");
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
const submitButton = document.getElementById("newproject-form-submitBtn");
if (submitButton) {
    submitButton.addEventListener("click", () => {
        sendData();
    });
}
// Expose function globally
window.showCreatedProjectModal = showCreatedProjectModal;
