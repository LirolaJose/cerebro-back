function getCurrentUser() {
    $.ajax({
        type: "GET",
        url: CURRENT_USER
    }).done(function (resp) {
        console.log(resp);
        return resp;
    });
}