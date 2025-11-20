let removeProjectId: string = "";

async function removeProject(projectId: string) {
    removeProjectId = projectId;
    const removeProjectDialog = document.getElementById("removeproject-dialog") as HTMLDialogElement;

    if (!removeProjectDialog) {
        console.debug("removeproject-dialog not found");
        return;
    }

    removeProjectDialog.showModal();
}

async function removeProjectRequest() {
    const params = new URLSearchParams();
    params.append("action", "deleteproject");
    params.append("projectId", removeProjectId);

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
                const removeProjectDialog = document.getElementById("removeproject-dialog") as HTMLDialogElement;
                removeProjectDialog.close();
                
                // Trigger HTMX refresh event
                if ((window as any).htmx) {
                    (window as any).htmx.trigger("#project-list", "RefreshProjectList");
                } else {
                    // Fallback: manually refresh the project list
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
        console.error("Error:", error);
    }
}

const removeProjectSubmitButton = document.getElementById("removeproject-submitButton") as HTMLButtonElement;

if (removeProjectSubmitButton) {
    removeProjectSubmitButton.addEventListener("click", () => {
        removeProjectRequest();
    });
}

// Expose function globally
(window as any).removeProject = removeProject;
