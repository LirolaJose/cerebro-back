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
        dataType: "json",
        url: SERVER_URL + "/registration/",
        contentType: "application/json",

        success: function (data) {
            console.log(data);
            window.location.href = "advertisementsList.html"
        },
        error: function (err){
            alert(err.responseText);
        }
    });
}

