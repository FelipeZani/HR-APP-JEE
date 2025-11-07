"use strict";
const submitButton = document.getElementById("newemployee-form-submitBtn");
submitButton.addEventListener("click", () => {
    sendData();
});
async function sendData() {
    const form = document.querySelector("#newemployee-form");
    if (!form) {
        console.log("Form not found");
        return;
    }
    // if (!form.checkValidity()) {
    //     console.log("Form is not valid");
    //     return;
    // }
    const formData = new FormData(form);
    const newEmployee = {
        action: formData.get("action"),
        name: `${formData.get("name")} ${formData.get("lastname")}`,
        post: formData.get("post"),
        department: formData.get("department"),
        rank: formData.get("rank")
    };
    const newEmployeeURL = new URLSearchParams(newEmployee);
    console.log(newEmployeeURL.toString());
    try {
        const formData = new FormData(form);
        if (formData) {
            const response = await fetch("http://localhost:8080/app/employeeServlet", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: newEmployeeURL.toString(),
            });
            //console.log(await response.json()); //Add the response to the div in the 
        }
        else {
            console.error("Problem with formData");
        }
    }
    catch (e) {
        console.error(e);
    }
}
