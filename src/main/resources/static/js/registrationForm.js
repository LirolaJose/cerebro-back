function addNewUserInfo() {
    $("#create-user-button").attr("disabled", true);
    let userInfo = {
        firstName: $('#firstName').val(),
        secondName: $('#secondName').val(),
        phone: $('#phone').val(),
        email: $('#email').val(),
        password: $('#password').val()
    }

    $.ajax({
        type: "POST",
        data: JSON.stringify(userInfo),
        url: SERVER_URL + "/registration/",
        contentType: "application/json"
    })
        .done(function (data) {
            console.log(data);
            window.location.href = "advertisementsList.html";
        })
        .fail(function (err) {
            $("#create-user-button").attr("disabled", false);
            alert(err.responseJSON.message)
        });
}

