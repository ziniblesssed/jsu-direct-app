import React, { useState } from "react";
import './App.css';


function App() {
    // React States
    const [errorMessages, setErrorMessages] = useState({});
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [backendWorking, setBackendWorking] = useState(false);
    const [loginWorking, setLoginWorking] = useState(false);

    // User Login info
    const database = [
        {
            username: "user1",
            password: "pass1"
        },
        {
            username: "user2",
            password: "pass2"
        }
    ];

    const errors = {
        uname: "invalid username",
        pass: "invalid password"
    };

    const handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();

        var { uname, pass } = document.forms[0];

        // Find user login info
        const userData = database.find((user) => user.username === uname.value);

        // Compare user info
        if (userData) {
            if (userData.password !== pass.value) {
                // Invalid password
                setErrorMessages({ name: "pass", message: errors.pass });
            } else {
                setIsSubmitted(true);
            }
        } else {
            // Username not found
            setErrorMessages({ name: "uname", message: errors.uname });
        }
    };

    // Generate JSX code for error message
    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error">{errorMessages.message}</div>
        );

    // JSX code for login form
    const renderForm = (
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="input-container">
                    <label>Username </label>
                    <input type="text" name="uname" required />
                    {renderErrorMessage("uname")}
                </div>
                <div className="input-container">
                    <label>Password </label>
                    <input type="password" name="pass" required />
                    {renderErrorMessage("pass")}
                </div>
                <div className="button-container">
                    <input type="submit" />
                </div>
            </form>
        </div>
    );

    async function testBackend() {
        var response
        await fetch('http://127.0.0.1:5000/api/test', { method: 'GET', headers: { Accept: 'application/json', 'Content-Type': 'application/json' }, }).then((response) => response.json()).then(data => { console.log(data); response = data })
        console.log(response)
        if (response['Message'] === 'Backend working') {setBackendWorking(true)}
    }

    async function login(user_id, password) {
        var response
        var data = { "password": "adminpassword" } // this will need to get the data from the password field
        // The username below (J00000000) in the address line will need to gotten from the username field
        await fetch('http://127.0.0.1:5000/api/login/J00000000', { method: 'POST', mode: 'cors', headers: { Accept: 'application/json', "Content-Type": 'application/json' }, body: JSON.stringify(data), }).then((response) => response.json()).then(data => { console.log(data); response = data })
        console.log(response)
        setLoginWorking(true)
    }

    return (
        <div className="app">
            <header className="App-header">
                <p> Senior Project Backbone using Reactjs as Backbone</p>
            </header>
            <div className="login-form">
                <div className="title">Sign In</div>
                {isSubmitted ? <div>User is successfully logged in</div> : renderForm}
            </div>
            <div className="login-form">
                <div className="title">Backend Test</div>
                <button onClick={testBackend}>Click to test backend</button>
                {backendWorking ? <div>Backend is working</div> : <div>Backend is not working</div>}
            </div>
            <div className="login-form">
                <div className="title">Hard coded login test</div>
                <button onClick={login} >Click to test login</button>
                {loginWorking ? <div>Login is working</div> : <div>Login is not working</div>}
            </div>
        </div>
    );
}

export default App;