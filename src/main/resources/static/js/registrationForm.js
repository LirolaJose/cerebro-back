function addNewUserInfo(){
    let firstName = $('#firstName').val();
    let secondName = $('#secondName').val();
    let phone = $('#phone').val();
    let email = $('#email').val();
    let password = $('#password').val();
    // let userInfo = {
    //     firstName: $('#firstName').val(),
    //     secondName: $('#secondName').val(),
    //     phone: $('#phone').val(),
    //     email: $('#email').val(),
    //     password: $('#password').val()
    // }


    $.ajax({
        type: "POST",
        data: JSON.stringify({firstName: firstName, secondName: secondName, phone: phone, email: email, password: password}),
        url: "http://localhost:8080/registration/",
        contentType: "application/json"
    });
}