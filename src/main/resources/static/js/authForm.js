function addNewUserInfo() {
    $("#create-user-button").attr("disabled", true);
    let userInfo = {
        firstName: $("#firstName").val(),
        secondName: $("#secondName").val(),
        phone: $("#phone").val(),
        email: $("#email").val(),
        password: $("#password").val()
    }

    $.ajax({
        type: "POST",
        data: JSON.stringify(userInfo),
        url: REGISTRATION,
        contentType: "application/json"
    })
        .done(function (data) {
            console.log(data);
            redirectToHome();
        })
        .fail(function (err) {
            $("#create-user-button").attr("disabled", false);
            alert(err.responseJSON.message);
        });
}

function loginUser() {
    let credentials = {
        login: $("#email-login").val(),
        password: $("#password-login").val()
    }
    $.ajax({
        type: "POST",
        data: JSON.stringify(credentials),
        url: LOGIN,
        contentType: "application/json"
    })
        .done(function (data) {
            localStorage.setItem("token", data.value);
            redirectToHome();
        })
        .fail(function (err) {
            console.log(err);
            alert(err.responseJSON.message);
        });
}

function logoutUser(){
    let token = localStorage.getItem("token");
    $.ajax({
        type: "POST",
        data: JSON.stringify(token),
        url: LOGOUT,
        contentType: "application/json"
    })
        .done(function (data) {
            localStorage.removeItem("token")
            redirectToHome();
        })
        .fail(function (err) {
            console.log(err);
            alert(err.responseJSON.message);
        });
}

