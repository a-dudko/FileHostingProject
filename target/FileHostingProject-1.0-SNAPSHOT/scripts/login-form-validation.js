function submitForm() {

    if (!validateUserLoginForm()) {
        return;
    }
    document.forms["userLoginForm"].submit();
}
function validateUserLoginForm() {
    var errorMessages = [];

    var loginElement = document.getElementById("login");
    loginElement.style.backgroundColor = "white";
    if (loginElement.value.length < 6 || loginElement.value.length > 300) {
        errorMessages.push("Login length should be 6 - 300 symbols");
        loginElement.style.backgroundColor = "pink";
    }
    else
    if (!loginElement.value.match(/[a-zA-Z0-9]+/)) {
        errorMessages.push("<br>Login should consist of English numbers and letters only");
        loginElement.style.backgroundColor = "pink";
    }

    var passwordElement = document.getElementById("password");
    passwordElement.style.backgroundColor = "white";
    if (passwordElement.value.length < 6 || passwordElement.value.length > 300) {
        errorMessages.push("<br>Password length should be 6 - 300 symbols");
        passwordElement.style.backgroundColor = "pink";
    }

    document.getElementById("messages").innerHTML = errorMessages;
    return errorMessages.length == 0;
}