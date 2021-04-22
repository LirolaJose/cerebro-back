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
        url: "http://localhost:8080/registration/",
        contentType: "application/json"
    });
}
