
export function removeEmployee(id: string) {

    const removeEmployeeDialog = document.getElementById("removeemployee-dialog") as HTMLDialogElement;
    const removeEmployeeSubmitButton = document.getElementById("removeemployee-submitButton");


    if (id === "") {
        console.log("Id can't be an empty string at removeEmployee()");
        return;
    }

    if (!removeEmployeeDialog) {
        console.log("removeemployeeDialog not found");
        return;
    }


    if (!removeEmployeeSubmitButton) {
        console.log("removeEmployeeSubmitButton was not found");
        return;
    }


    removeEmployeeDialog.showModal();

    if (typeof (removeEmployeeSubmitButton!!.onclick) != "function") {
        removeEmployeeSubmitButton!!.onclick =  async () => removeEmployeeRequest(id, removeEmployeeSubmitButton, removeEmployeeDialog);

    }


}



async function removeEmployeeRequest(id: string, removeEmployeeSubmitButton: HTMLElement, removeEmployeeDialog: HTMLDialogElement) {

    const urlSearchParams = new URLSearchParams({
        action: "removeemployee",
        employeeid: id
    });
    try {
        const response = await fetch("http://localhost:8080/app/employeeServlet?" + urlSearchParams.toString(), {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("RemoveEmployeeRequest failed");
        }

        const removedRow = document.querySelector(`[data-id='${id}']`);
        console.log(removedRow);

        removeEmployeeSubmitButton.onclick = null;
        removedRow!!.remove();

        removeEmployeeDialog.close();

    } catch (error) {
        console.error(error);
    }
}

//expose function globabally
; (window as any).removeEmployee = removeEmployee;
