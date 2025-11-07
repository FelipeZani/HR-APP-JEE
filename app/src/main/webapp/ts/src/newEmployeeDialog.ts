
const submitButton = document.getElementById("newemployee-form-submitBtn") as HTMLButtonElement;




submitButton.addEventListener("click", () => {

    sendData();

});

async function sendData() {
    const form = document.querySelector("#newemployee-form") as HTMLFormElement;

    if (!form) {
        console.log("Form not found");
        return;
    }

    // if (!form.checkValidity()) {
    //     console.log("Form is not valid");
    //     return;
    // }

    const formData = new FormData(form);

    const newEmployee: any = {
        action:formData.get("action"),
        name: `${formData.get("name") as string} ${formData.get("lastname") as string}`,
        post: formData.get("post") as string,
        department: formData.get("department") as string,
        rank: formData.get("rank") as string

    }
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
        }else{
            console.error("Problem with formData");
        }
    } catch (e) {
        console.error(e);
    }




}