function addMoney(userInfoId, money) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({value: money}),
        beforeSend: function (xhr){
            setTokenToHeader(xhr)
        },
        url: SERVER_URL + "/api/user/" + userInfoId + "/money/",
        contentType: "application/json",
    })
        .done(function (resp) {
            console.log(resp);
            window.location.reload();
        })
        .fail(function (err) {
            console.log(err)
            alert(err.responseJSON.message)
        });
}