


const openDialogButtonIds: ReadonlyArray<string> = Array.of("searchemployee", "newemployee","newpost");

openDialogButtonIds.forEach((dialogId) => {
    const openDialogButton = document.getElementById(`${dialogId}-opendialogbutton`) as HTMLButtonElement;
    const dialogElement = document.getElementById(`${dialogId}-dialog`) as HTMLDialogElement;

    if (openDialogButton) {
        openDialogButton.addEventListener("click", () => {
            dialogElement.showModal();

        });
        
    } else {
        console.log("OpenDialogButton not found: " + openDialogButton);
    }

});


const dialogrelatedId: ReadonlyArray<string> = Array.of("searchemployee", "newemployee", "modifyemployee", "removeemployee", "newlycreatedemployee","newpost");


dialogrelatedId.forEach((dialogId) => {
    
    const closeDialogButton = document.getElementById(`${dialogId}-closeButton`) as HTMLButtonElement;
    const dialogElement = document.getElementById(`${dialogId}-dialog`) as HTMLDialogElement;
    const formDialog = document.getElementById(`${dialogId}-form`) as HTMLFormElement;


    if (dialogElement && formDialog && closeDialogButton) {

        dialogElement.addEventListener("click", () => {
            dialogElement.close();

        });

        closeDialogButton.addEventListener("click", () => {
            dialogElement.close();

        });


        formDialog.addEventListener("click", (e: Event) => {
            e.stopPropagation();
        });
    } else {
        console.log("HTML Element not found : " + dialogElement + " " + formDialog + " " + closeDialogButton);
    }
});



