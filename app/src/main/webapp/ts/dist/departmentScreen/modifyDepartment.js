export function modifyDepartment(id) {
    console.log("modifyDepartment called with id:", id);
    const modifyDepartmentDialog = document.getElementById("modifydepartment-dialog");
    const modifyDepartmentSubmitButton = document.getElementById("modifyDepartment-form-submitBtn");
    if (id === "") {
        console.log("Id can't be an empty string at modifyDepartment()");
        return;
    }
    if (!modifyDepartmentSubmitButton) {
        console.log("ModifyDepartmentSubmitBtn not found");
        return;
    }
    if (!modifyDepartmentDialog) {
        console.log("modifydepartmentDialog not found");
        return;
    }
    // Charger les données du département
    loadDepartmentData(id);
    // Afficher le dialog
    modifyDepartmentDialog.showModal();
    // Remplacer le handler onclick (évite les doublons)
    modifyDepartmentSubmitButton.onclick = async (e) => {
        e.preventDefault();
        await modifyDepartmentRequest(id, modifyDepartmentDialog);
    };
}
function loadDepartmentData(id) {
    console.log("Loading department data for id:", id);
    if (id === "") {
        console.log("Id can't be empty");
        return;
    }
    const rowDiv = document.querySelector(`[data-id="${id}"]`);
    if (!rowDiv) {
        console.error("Department row not found for id:", id);
        return;
    }
    // Extraire les données du département depuis le DOM
    const strongElement = rowDiv.querySelector('strong');
    const nameText = strongElement?.textContent?.split('(')[0].trim() || '';
    const managerDiv = rowDiv.querySelector('div:nth-child(2)');
    const managerFullText = managerDiv?.textContent || '';
    const managerName = managerFullText.replace('Manager:', '').trim();
    // Attendre que les champs soient disponibles
    waitForFields(id, nameText, managerName);
    console.log("Department data extracted:", { id, name: nameText, manager: managerName });
}
function waitForFields(id, nameText, managerName) {
    let attempts = 0;
    const maxAttempts = 10;
    const tryFillFields = () => {
        attempts++;
        const inputId = document.getElementById("modify-dep-id");
        const inputName = document.getElementById("modify-dep-name");
        const selectManager = document.getElementById("modify-dep-manager");
        if (!inputId || !inputName || !selectManager) {
            console.warn(`Fields not found yet (attempt ${attempts}/${maxAttempts})`);
            if (attempts < maxAttempts) {
                setTimeout(tryFillFields, 100);
            }
            else {
                console.error("Timeout waiting for form fields");
            }
            return;
        }
        // Remplir les champs
        inputId.value = id;
        inputName.value = nameText;
        console.log("Fields filled successfully");
        // Attendre que HTMX charge les options du select avant de sélectionner
        waitForManagerOptions(selectManager, managerName);
    };
    tryFillFields();
}
function waitForManagerOptions(selectElement, managerName) {
    let attempts = 0;
    const maxAttempts = 20;
    const trySelect = () => {
        attempts++;
        // Vérifier si les options sont chargées (plus que l'option par défaut)
        if (selectElement.options.length <= 1) {
            if (attempts < maxAttempts) {
                console.log(`Options not loaded yet (attempt ${attempts}), retrying...`);
                setTimeout(trySelect, 100);
            }
            else {
                console.warn("Timeout waiting for manager options to load");
            }
            return;
        }
        // Si pas de manager assigné
        if (managerName === '--' || managerName === '') {
            selectElement.value = '';
            console.log("No manager assigned to this department");
            return;
        }
        // Chercher et sélectionner l'option correspondante
        let found = false;
        for (let i = 0; i < selectElement.options.length; i++) {
            const option = selectElement.options[i];
            if (option.text.trim() === managerName) {
                selectElement.value = option.value;
                console.log("Manager selected:", managerName, "with id:", option.value);
                found = true;
                break;
            }
        }
        if (!found) {
            console.warn("Manager not found in options:", managerName);
            console.log("Available options:", Array.from(selectElement.options).map((o) => o.text));
            selectElement.value = '';
        }
    };
    // Écouter l'événement HTMX
    selectElement.addEventListener('htmx:afterSwap', () => {
        console.log("HTMX finished loading manager options");
        setTimeout(trySelect, 50);
    }, { once: true });
    // Lancer la première tentative
    setTimeout(trySelect, 150);
}
async function modifyDepartmentRequest(id, modifyDepartmentDialog) {
    console.log("Starting modify department request for id:", id);
    const inputName = document.getElementById("modify-dep-name");
    const selectManager = document.getElementById("modify-dep-manager");
    if (!inputName || !selectManager) {
        console.error("Input fields not found in modify request");
        alert("Error: Form fields not found");
        return;
    }
    const name = inputName.value.trim();
    const managerId = selectManager.value;
    if (!name || name === "") {
        alert("Please enter a department name");
        return;
    }
    console.log("Submitting:", {
        departmentId: id,
        name: name,
        managerId: managerId || '(none)'
    });
    const params = new URLSearchParams();
    params.append("action", "modifydepartment");
    params.append("departmentId", id);
    params.append("name", name);
    if (managerId && managerId !== "") {
        params.append("managerId", managerId);
    }
    try {
        const response = await fetch("/app/departmentServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: params.toString(),
        });
        console.log("Response status:", response.status);
        const responseText = await response.text();
        console.log("Response text:", responseText);
        if (!response.ok) {
            throw new Error(`Server error: ${response.status} - ${responseText}`);
        }
        if (responseText !== "OK") {
            console.warn("Unexpected response:", responseText);
        }
        console.log("✓ Modification successful");
        // Fermer le dialog
        modifyDepartmentDialog.close();
        // Réinitialiser le formulaire
        const form = document.getElementById("modifydepartment-form");
        if (form) {
            form.reset();
        }
        // Rafraîchir la liste avec HTMX
        if (window.htmx) {
            console.log("Triggering HTMX refresh...");
            window.htmx.trigger("#dept-list", "RefreshDepartmentList");
        }
        else {
            console.log("HTMX not found, manually refreshing...");
            const deptList = document.getElementById("dept-list");
            if (deptList) {
                const refreshResponse = await fetch("/app/departmentServlet?action=getdepartmentfulllist");
                const html = await refreshResponse.text();
                deptList.innerHTML = html;
            }
        }
        alert("Department modified successfully!");
    }
    catch (error) {
        console.error("❌ Error modifying department:", error);
        const errorMessage = error instanceof Error ? error.message : String(error);
        alert("Error modifying department: " + errorMessage);
    }
}
// Exposer la fonction globalement
window.modifyDepartment = modifyDepartment;
