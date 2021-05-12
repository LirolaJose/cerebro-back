function addMoney(userInfoId, money) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({value: money}),
        dataType: "json",
        url: SERVER_URL + "/api/user/" + userInfoId + "/money/",
        contentType: "application/json"
    //     success: function (resp) {
    //         console.log(resp);
    //         window.location.reload();
    //         alert("money increased")
    //     },
    //     error: function (err) {
    //         console.log(err)
    //         alert("money not increased")
    //     }
    //
    // })
    //     .done(function (resp) {
    //         console.log(resp);
    //         window.location.reload();
    //         alert("money increased")
    //     })
    //     .fail(function (err) {
    //         console.log(err)
    //         alert("money not increased")
    //     });
    })
}