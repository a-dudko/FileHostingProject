function submitForm() {

    if (!validateFileUploadForm()) {
        return;
    }
    document.getElementById("action").value = 'save';
    document.forms["fileUploadForm"].submit();
}
function validateFileUploadForm() {
    var errorMessages = [];
    var fileElement = document.getElementById("file");
    fileElement.style.backgroundColor = "white";
    if (fileElement.value == '') {
        errorMessages.push("File should be chosen");
        fileElement.style.backgroundColor = "red";
    }
    var descriptionElement = document.getElementById("description");
    descriptionElement.style.backgroundColor = "white";
    if (descriptionElement.value == '') {
        errorMessages.push("<br>Description field should not be empty");
        descriptionElement.style.backgroundColor = "red";
    }
    var authorElement = document.getElementById("author");
    authorElement.style.backgroundColor = "white";
    if (authorElement.value == '') {
        errorMessages.push("<br>Author field should not be empty");
        authorElement.style.backgroundColor = "red";
    }
    var notesElement = document.getElementById("notes");
    notesElement.style.backgroundColor = "white";
    if (notesElement.value == '') {
        errorMessages.push("<br>Notes field should not be empty");
        notesElement.style.backgroundColor = "red";
    }
    document.getElementById("messages").innerHTML = errorMessages;
    return errorMessages.length == 0;
}