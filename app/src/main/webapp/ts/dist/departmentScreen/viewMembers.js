export function viewDepartmentMembers(departmentId) {
    console.log("viewDepartmentMembers called with id:", departmentId);
    const membersDialog = document.getElementById("members-dialog");
    const membersContent = document.getElementById("members-dialog-content");
    const closeButton = document.getElementById("members-closeButton");
    if (!membersDialog || !membersContent || !closeButton) {
        console.error("Dialog elements not found");
        alert("Error: Dialog elements not found");
        return;
    }
    // Show loading message
    membersContent.innerHTML = "<p>Loading members...</p>";
    membersDialog.showModal();
    // Fetch department members
    fetch(`/app/departmentServlet?action=getdepartmentemployees&departmentId=${departmentId}`)
        .then((response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.text();
    })
        .then((html) => {
        console.log("Members loaded successfully");
        membersContent.innerHTML = html;
    })
        .catch((error) => {
        console.error("Error loading members:", error);
        membersContent.innerHTML = `<p style="color: red;">Error loading members: ${error}</p>`;
    });
    // Close dialog handling
    closeButton.onclick = () => {
        membersDialog.close();
    };
    // Also close on clicking outside the dialog
    membersDialog.onclick = () => {
        membersDialog.close();
    };
    membersContent.onclick = (e) => {
        e.stopPropagation();
    };
}
// Expose function globally
window.viewDepartmentMembers = viewDepartmentMembers;
