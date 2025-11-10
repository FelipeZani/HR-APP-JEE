
import { NewlyCreatedEmployeeType } from './types'
const submitButton = document.getElementById("newemployee-form-submitBtn") as HTMLButtonElement;




submitButton.addEventListener("click", () => {

    sendData();

});

async function sendData() {
    
    const form = document.querySelector("#newemployee-form") as HTMLFormElement;
    const dialog = document.querySelector("#newemployee-dialog") as HTMLDialogElement;
    const dialog2 = document.querySelector("newlycreatedemployee-dialog") as HTMLDialogElement;
    if(!dialog || !dialog2){
        console.log("Form not found");
        return;
    }
    if(!form) {
        console.log("Form not found");
        return;
    }

    if(!form.checkValidity()) {
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

            if(await response.ok){
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
                
                dialog.close();
                

            }else{
                console.log(await response.statusText);
            }

            


            console.log(await response.json());
        } else {
            console.error("Problem with formData");
        }
    } catch (e) {
        console.error(e);
    }




}
//vibe coded late at night
function showCreatedEmployeeModal(emp: NewlyCreatedEmployeeType) {
    const usernameP = document!!.getElementById("newlycreatedemployee-username");
    const passwordP = document!!.getElementById("newlycreatedemployee-password");

    if (usernameP) usernameP.textContent = emp.username;
    if (passwordP) passwordP.textContent = emp.password;
}

function appendEmployeeToHome(emp: NewlyCreatedEmployeeType) {
    const bodyTable = document.getElementById("bodytable");
    if (!bodyTable) return;

    // Create a new row container
    const rowDiv = document.createElement("div");
    rowDiv.className = "row";

    // Create cells
    const nameDiv = document.createElement("div");
    nameDiv.textContent = emp.name;

    const postDiv = document.createElement("div");
    postDiv.textContent = emp.post;

    const departmentDiv = document.createElement("div");
    departmentDiv.textContent = emp.department;

    const rankDiv = document.createElement("div");
    rankDiv.textContent = emp.rank;

    const actionDiv = document.createElement("div");
    // you can add buttons or actions here
    const viewBtn = document.createElement("button");
    viewBtn.textContent = "View";
    viewBtn.onclick = () => showCreatedEmployeeModal(emp); // optional
    actionDiv.appendChild(viewBtn);

    // Append all cells to the row
    rowDiv.appendChild(nameDiv);
    rowDiv.appendChild(postDiv);
    rowDiv.appendChild(departmentDiv);
    rowDiv.appendChild(rankDiv);
    rowDiv.appendChild(actionDiv);

    // Append the row to the table
    bodyTable.appendChild(rowDiv);
}