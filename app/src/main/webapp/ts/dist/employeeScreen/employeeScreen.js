"use strict";
const openDialogButtonIds = Array.of("searchemployee", "newemployee", "newpost");
openDialogButtonIds.forEach((dialogId) => {
    const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`);
    const dialogElement = document.getElementById(`${dialogId}-dialog`);
    if (openDialogButton) {
        openDialogButton.addEventListener("click", () => {
            dialogElement.showModal();
        });
    }
    else {
        console.log("OpenDialogButton not found: " + openDialogButton);
    }
});
const dialogrelatedId = Array.of("searchemployee", "newemployee", "modifyemployee", "removeemployee", "newlycreatedemployee", "newpost");
dialogrelatedId.forEach((dialogId) => {
    const closeDialogButton = document.getElementById(`${dialogId}-closeButton`);
    const dialogElement = document.getElementById(`${dialogId}-dialog`);
    const formDialog = document.getElementById(`${dialogId}-form`);
    if (dialogElement && formDialog && closeDialogButton) {
        dialogElement.addEventListener("click", () => {
            dialogElement.close();
        });
        closeDialogButton.addEventListener("click", () => {
            dialogElement.close();
        });
        formDialog.addEventListener("click", (e) => {
            e.stopPropagation();
        });
    }
    else {
        console.log("HTML Element not found : " + dialogElement + " " + formDialog + " " + closeDialogButton);
    }
});
// --- Gestion du bouton Dashboard ---
const dashboardBtn = document.getElementById('dashboard-btn');
if (dashboardBtn) {
    dashboardBtn.addEventListener('click', (e) => {
        e.preventDefault(); // Empêche le rechargement de page si c'est dans un form
        // Ouvre le servlet de génération PDF avec le paramètre type=dashboard
        window.open('report/employees?type=dashboard', '_blank');
    });
}
else {
    console.log("Bouton Dashboard introuvable (vérifie l'ID dans le HTML)");
}
