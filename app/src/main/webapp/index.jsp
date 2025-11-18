<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>

    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">

    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <script src="https://cdn.jsdelivr.net/npm/htmx.org@2.0.8/dist/htmx.min.js" 
                integrity="sha384-/TgkGk7p307TH7EXJDuUlgG3Ce1UVolAOFopFekQkkXihi5u/6OCvVKyz1W+idaz" crossorigin="anonymous">
        </script>


        <title>Document</title>
        <link rel="stylesheet" href="/css/navBar.css" type="text/css">
        <link rel="stylesheet" href="./css/index.css">
    </head>

    <body>
        <main>
            <div id="login-container">
                <form action="" method="get" class="styled-form"
                    hx-get="http://localhost:8080/app/employeeServlet"
                    hx-target="#login-response"
                    hx-swap="innerHTML"
                    hx-redirect=""
                    >
                    <input type="hidden" name="action" value="login">
                    <label> Login:<input type="text" name="username"></label>
                    <label>Password: <input type="password" name="password"></label>
                    <input type="submit" value="submit" id="login-submitbtn">
                </form>
                <p id="login-response"></p>
            </div>            
        </main>
        <button onclick="location.href='http:\/\/localhost:8080/app/employeeScreen.html'">EmployeePage</button>
    </body>

    </html>