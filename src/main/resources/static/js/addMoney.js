function addMoney(userInfoId, money){
    $.ajax({
        type: "POST",
        data: JSON.stringify({value: money}),
        dataType: "json",
        url: SERVER_URL + "/api/user/" + userInfoId + "/money/",
        contentType: "application/json",
    }).done(function (resp){
        console.log(resp);
        window.location.replace();
    })
}