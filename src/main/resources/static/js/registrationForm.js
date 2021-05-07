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
        //fixme localhost
        url: "http://localhost:8080/registration/",
        contentType: "application/json",

        // success: function (data) {
        //     console.log(data);
        //     window.location.href = "advertisementsList.html"
        // },
        // error: function (err){
        //     alert(err.responseJSON.message);
        // }
    });
}

