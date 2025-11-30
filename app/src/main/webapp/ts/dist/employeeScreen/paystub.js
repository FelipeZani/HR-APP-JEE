export function openPayStubDialog(employeeId, employmentDate) {
    const dialog = document.getElementById("paystub-dialog");
    const idInput = document.getElementById("paystub-employee-id");
    const periodInput = document.getElementById("paystub-period");
    const closeBtn = document.getElementById("paystub-closeButton");
    const errorMsg = document.getElementById("paystub-error");
    if (!dialog || !idInput || !periodInput)
        return;
    // Set employee ID
    idInput.value = employeeId;
    // Convert employment date (dd-MM-yyyy) to YYYY-MM for the min attribute
    const [day, month, year] = employmentDate.split('-');
    const minMonth = `${year}-${month}`; // Format YYYY-MM
    periodInput.min = minMonth;
    periodInput.value = ""; // Reset value
    errorMsg.style.display = "none";
    dialog.showModal();
    // Validation on change
    periodInput.onchange = () => {
        if (periodInput.value < minMonth) {
            errorMsg.textContent = `La date ne peut pas être antérieure à l'embauche (${employmentDate})`;
            errorMsg.style.display = "block";
            periodInput.setCustomValidity("Invalid date");
        }
        else {
            errorMsg.style.display = "none";
            periodInput.setCustomValidity("");
        }
    };
    // Close logic
    const closeHandler = () => dialog.close();
    closeBtn.onclick = closeHandler;
    // Auto-close dialog after submission (optional, as PDF opens in new tab)
    const form = document.getElementById("paystub-form");
    form.onsubmit = () => {
        setTimeout(() => dialog.close(), 500); // Close shortly after submit
    };
}
// Expose globally
window.openPayStubDialog = openPayStubDialog;
