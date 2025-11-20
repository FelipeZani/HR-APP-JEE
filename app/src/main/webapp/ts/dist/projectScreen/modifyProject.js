"use strict";
async function modifyProject(projectId) {
    console.debug("modifyProject called with id:", projectId);
    const modifyProjectDialog = document.getElementById("modifyproject-dialog");
    const modifyProjectSubmitButton = document.getElementById("modifyproject-form-submitBtn");
    if (!projectId) {
        console.debug("Id can't be an empty string at modifyProject()");
        return;
    }
    if (!modifyProjectSubmitButton) {
        console.debug("ModifyProjectSubmitBtn not found");
        return;
    }
    if (!modifyProjectDialog) {
        console.debug("modifyprojectDialog not found");
        return;
    }
    // Set the project ID
    const projectIdInput = document.getElementById("modifyproject-id");
    projectIdInput.value = projectId;
    // Fetch project data
    try {
        const response = await fetch(`/app/projectServlet?action=getprojectinfo&projectId=${projectId}`);
        if (response.ok) {
            // Wait for form fields to be available
            await waitForFields();
            // Populate the form
            const labelInput = document.getElementById("modifyproject-label");
            const statusSelect = document.getElementById("modifyproject-status");
            const deadlineInput = document.getElementById("modifyproject-deadline");
            if (labelInput && statusSelect && deadlineInput) {
                // Note: You would populate these with actual project data
                // For now, this is a placeholder
            }
        }
    }
    catch (error) {
        console.error("Failed to load project data:", error);
    }
    modifyProjectDialog.showModal();
    modifyProjectSubmitButton.onclick = async (e) => {
        e.preventDefault();
        await modifyProjectRequest(projectId, modifyProjectDialog);
    };
}
async function waitForFields(maxAttempts = 10) {
    let attempts = 0;
    while (attempts < maxAttempts) {
        const labelInput = document.getElementById("modifyproject-label");
        const statusSelect = document.getElementById("modifyproject-status");
        if (labelInput && statusSelect) {
            return; // Fields are ready
        }
        await new Promise(resolve => setTimeout(resolve, 100)); // Wait 100ms
        attempts++;
    }
    console.warn("Form fields did not load within timeout");
}
async function modifyProjectRequest(projectId, dialog) {
    const form = document.getElementById("modifyproject-form");
    if (!form) {
        console.error("Form not found");
        return;
    }
    const formData = new FormData(form);
    const params = new URLSearchParams();
    params.append("action", formData.get("action"));
    params.append("projectId", projectId);
    params.append("label", formData.get("label"));
    params.append("status", formData.get("status"));
    params.append("deadLine", formData.get("deadLine"));
    params.append("managerId", formData.get("managerId"));
    try {
        const response = await fetch("/app/projectServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: params.toString(),
        });
        if (response.ok) {
            const responseText = await response.text();
            if (responseText === "OK") {
                dialog.close();
                // Trigger HTMX refresh event
                if (window.htmx) {
                    window.htmx.trigger("#project-list", "RefreshProjectList");
                }
                else {
                    // Fallback: manually refresh
                    const projectList = document.getElementById("project-list");
                    if (projectList) {
                        const listResponse = await fetch("/app/projectServlet?action=getprojectlist");
                        const html = await listResponse.text();
                        projectList.innerHTML = html;
                    }
                }
            }
        }
    }
    catch (error) {
        console.error("Error modifying project:", error);
    }
}
// Expose function globally
window.modifyProject = modifyProject;
