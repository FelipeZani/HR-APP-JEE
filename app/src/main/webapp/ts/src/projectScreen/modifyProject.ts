async function modifyProject(projectId: string) {
    console.debug("modifyProject called with id:", projectId);
    
    const modifyProjectDialog = document.getElementById("modifyproject-dialog") as HTMLDialogElement;
    const modifyProjectSubmitButton = document.getElementById("modifyproject-form-submitBtn") as HTMLButtonElement;

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
    const projectIdInput = document.getElementById("modifyproject-id") as HTMLInputElement;
    projectIdInput.value = projectId;

    // Fetch project data
    try {
        const response = await fetch(`/app/projectServlet?action=getprojectinfo&projectId=${projectId}`);
        if (response.ok) {
            // Wait for form fields to be available
            await waitForFields();
            
            // Populate the form
            const labelInput = document.getElementById("modifyproject-label") as HTMLInputElement;
            const statusSelect = document.getElementById("modifyproject-status") as HTMLSelectElement;
            const deadlineInput = document.getElementById("modifyproject-deadline") as HTMLInputElement;
            
            if (labelInput && statusSelect && deadlineInput) {
                // Note: You would populate these with actual project data
                // For now, this is a placeholder
            }
        }
    } catch (error) {
        console.error("Failed to load project data:", error);
    }

    modifyProjectDialog.showModal();

    modifyProjectSubmitButton.onclick = async (e: Event) => {
        e.preventDefault();
        await modifyProjectRequest(projectId, modifyProjectDialog);
    };
}

async function waitForFields(maxAttempts: number = 10): Promise<void> {
    let attempts = 0;
    while (attempts < maxAttempts) {
        const labelInput = document.getElementById("modifyproject-label") as HTMLInputElement;
        const statusSelect = document.getElementById("modifyproject-status") as HTMLSelectElement;
        
        if (labelInput && statusSelect) {
            return; // Fields are ready
        }
        
        await new Promise(resolve => setTimeout(resolve, 100)); // Wait 100ms
        attempts++;
    }
    console.warn("Form fields did not load within timeout");
}

async function modifyProjectRequest(projectId: string, dialog: HTMLDialogElement) {
    const form = document.getElementById("modifyproject-form") as HTMLFormElement;
    
    if (!form) {
        console.error("Form not found");
        return;
    }

    const formData = new FormData(form);
    const params = new URLSearchParams();
    params.append("action", formData.get("action") as string);
    params.append("projectId", projectId);
    params.append("label", formData.get("label") as string);
    params.append("status", formData.get("status") as string);
    params.append("deadLine", formData.get("deadLine") as string);
    params.append("managerId", formData.get("managerId") as string);

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
                if ((window as any).htmx) {
                    (window as any).htmx.trigger("#project-list", "RefreshProjectList");
                } else {
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
    } catch (error) {
        console.error("Error modifying project:", error);
    }
}

// Expose function globally
(window as any).modifyProject = modifyProject;
