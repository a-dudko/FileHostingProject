function submitForm() {

    if (!validateUserRegistrationForm()) {
        return;
    }
    document.getElementById("action").value = 'register';
    document.forms["userRegistrationForm"].submit();
}
function validateUserRegistrationForm() {
    var errorMessages = [];

    var loginElement = document.getElementById("login");
    loginElement.style.backgroundColor = "white";
    if (loginElement.value.length < 6 || loginElement.value.length > 300) {
        errorMessages.push("<br>Login length should be 6 - 300 symbols");
        loginElement.style.backgroundColor = "red";
    }
    else
        if (!loginElement.value.match(/[a-zA-Z0-9]+/)) {
            errorMessages.push("<br>Login should consist of numbers and letters only");
            loginElement.style.backgroundColor = "red";
        }

    var passwordElement = document.getElementById("password");
    passwordElement.style.backgroundColor = "white";
    if (passwordElement.value.length < 6 || passwordElement.value.length > 300) {
        errorMessages.push("<br>Password length should be 6 - 300 symbols");
        passwordElement.style.backgroundColor = "red";
    }

    var repeatPasswordElement = document.getElementById("repeatPassword");
    repeatPasswordElement.style.backgroundColor = "white";
    if (repeatPasswordElement.value != passwordElement.value) {
        errorMessages.push("<br>Passwords should be equal");
        repeatPasswordElement.style.backgroundColor = "red";
    }

    var adminCodeElement = document.getElementById("adminCode");
    adminCodeElement.style.backgroundColor = "white";
    if (!adminCodeElement.value.match(/[a-zA-Z0-9]+/)) {
            errorMessages.push("<br>Admin code should consist of numbers and letters only");
            adminCodeElement.style.backgroundColor = "red";
    }

    document.getElementById("messages").innerHTML = errorMessages;
    return errorMessages.length == 0;
}