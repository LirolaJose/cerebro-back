function addNewUserInfo() {
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
            alert(err.responseText);
        });
}

