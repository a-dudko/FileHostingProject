function submitForm() {

    if (!validateFileUploadForm()) {
        return;
    }
    document.getElementById("action").value = 'upload';
    document.forms["fileUploadForm"].submit();
}
function validateFileUploadForm() {
    var errorMessages = [];
    var fileElement = document.getElementById("file");
    fileElement.style.backgroundColor = "white";
    if (fileElement.value == '') {
        errorMessages.push("File should be chosen");
        fileElement.style.backgroundColor = "pink";
    }

    var descriptionElement = document.getElementById("description");
    descriptionElement.style.backgroundColor = "white";
    if (descriptionElement.value == '') {
        errorMessages.push("<br>Description field should not be empty");
        descriptionElement.style.backgroundColor = "pink";
    }
    if (descriptionElement.value.length > 300) {
        errorMessages.push("<br>Field value length should be 1-300 symbols ");
        descriptionElement.style.backgroundColor = "pink";
    }

    var authorElement = document.getElementById("author");
    authorElement.style.backgroundColor = "white";
    if (authorElement.value == '') {
        errorMessages.push("<br>Author field should not be empty");
        authorElement.style.backgroundColor = "pink";
    }
    if (authorElement.value.length > 300) {
        errorMessages.push("<br>Field value length should be 1-300 symbols ");
        authorElement.style.backgroundColor = "pink";
    }

    var notesElement = document.getElementById("notes");
    notesElement.style.backgroundColor = "white";
    if (notesElement.value == '') {
        errorMessages.push("<br>Notes field should not be empty");
        notesElement.style.backgroundColor = "pink";
    }
    if (notesElement.value.length > 300) {
        errorMessages.push("<br>Field value length should be 1-300 symbols ");
        notesElement.style.backgroundColor = "pink";
    }

    document.getElementById("messages").innerHTML = errorMessages;
    return errorMessages.length == 0;
}