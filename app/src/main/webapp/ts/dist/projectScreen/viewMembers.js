"use strict";
async function viewProjectMembers(projectId) {
    console.debug("viewProjectMembers called with id:", projectId);
    const membersDialog = document.getElementById("members-dialog");
    const membersContent = document.getElementById("members-dialog-content");
    if (!membersDialog || !membersContent) {
        console.debug("Members dialog or content not found");
        return;
    }
    try {
        const response = await fetch(`/app/projectServlet?action=getprojectmembers&projectId=${projectId}`);
        if (response.ok) {
            const html = await response.text();
            membersContent.innerHTML = html;
            membersDialog.showModal();
        }
        else {
            console.error("Failed to fetch members:", response.statusText);
        }
    }
    catch (error) {
        console.error("Error fetching members:", error);
    }
}
// Expose function globally
window.viewProjectMembers = viewProjectMembers;
