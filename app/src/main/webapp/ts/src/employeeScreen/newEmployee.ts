
import { NewlyCreatedEmployeeType } from '../types.js'
import { modifyEmployee } from './modifyEmployee.js';
import{removeEmployee} from './removeEmployee.js'



const submitButton = document.getElementById("newemployee-form-submitBtn") as HTMLButtonElement;




submitButton.addEventListener("click", () => {

    sendData();

});

async function sendData() {

    const form = document.getElementById("newemployee-form") as HTMLFormElement;

    if (!form) {
        console.log("newemployee form not found");
        return;
    }

    if (!form.checkValidity()) {
        console.log("Form is not valid");
        return;
    }

    const formData = new FormData(form);

    const newEmployee: any = {
        action: formData.get("action"),
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
            if (response.ok) {
                const responseData = await response.json();
                const newlyCreatedEmployee: NewlyCreatedEmployeeType = {
                    id: responseData.id,
                    name: responseData.name,
                    post: responseData.post,
                    department: responseData.department,
                    rank: responseData.rank,
                    username: responseData.username,
                    password: responseData.password,

                }

                showCreatedEmployeeModal(newlyCreatedEmployee);
                appendEmployeeToHome(newlyCreatedEmployee);


            } else {
                console.log(response.statusText);
            }

        } else {
            console.error("Problem with formData");
        }
    } catch (e) {
        console.error(e);
    }




}

function showCreatedEmployeeModal(emp: NewlyCreatedEmployeeType) {
    const dialog = document.getElementById("newemployee-dialog") as HTMLDialogElement;
    const dialog2 = document.getElementById("newlycreatedemployee-dialog") as HTMLDialogElement;

    if (!dialog || !dialog2) {
        console.log("Dialog newemployee : ",dialog);
        console.log("Dialog newlycreated employee : ",dialog2);
        return;
    }

    const usernameP = document!!.getElementById("newlycreatedemployee-username");
    const passwordP = document!!.getElementById("newlycreatedemployee-password");


    if (usernameP) usernameP.textContent = emp.username;
    if (passwordP) passwordP.textContent = emp.password;

    dialog.close();
    dialog2.showModal();

}
function appendEmployeeToHome(emp: NewlyCreatedEmployeeType) {
    
    const bodyTable = document.getElementById("bodytable");
    
    if (!bodyTable) {
        console.log("BodyTable id not found")
        return;
    };

    const rowDiv = document.createElement("div");
    rowDiv.className = "row";
    rowDiv.dataset.id = `${emp.id}`;


    const nameDiv = document.createElement("div");
    nameDiv.textContent = emp.name;

    const postDiv = document.createElement("div");
    postDiv.textContent = emp.post;

    const departmentDiv = document.createElement("div");
    departmentDiv.textContent = emp.department;

    const rankDiv = document.createElement("div");
    rankDiv.textContent = emp.rank;

    const actionDiv = document.createElement("div");

    const modifyEmployeeButton = document.createElement("button");
    const deleteEmployeeButton = document.createElement("button");

    modifyEmployeeButton.textContent = "modify";
    deleteEmployeeButton.textContent = "remove";

    modifyEmployeeButton.onclick = () => modifyEmployee(emp.id.toString());
    deleteEmployeeButton.onclick = () => removeEmployee(emp.id.toString());

    actionDiv.appendChild(modifyEmployeeButton);
    actionDiv.appendChild(deleteEmployeeButton);

    rowDiv.appendChild(nameDiv);
    rowDiv.appendChild(postDiv);
    rowDiv.appendChild(departmentDiv);
    rowDiv.appendChild(rankDiv);
    rowDiv.appendChild(actionDiv);

    bodyTable.appendChild(rowDiv);
}

