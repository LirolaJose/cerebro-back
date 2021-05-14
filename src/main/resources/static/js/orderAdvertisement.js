function sendOrder(){
    $("#order-button").attr("disabled", true);
    let additionalServices = [];
    let advertisementId = getParameter("id");
    if(!advertisementId) {
        redirectToHome();
    }
    $(".additionalService").each(function (){
        if(this.checked){
            additionalServices.push($(this).val());
        }
    })
    let order = {
        advertisementId: advertisementId,
        additionalServicesId: additionalServices
    }
    $.ajax({
        type: "POST",
        data: JSON.stringify(order),
        url: SERVER_URL + "/api/order/",
        contentType: "application/json"
    }).done(function (data) {
        console.log(data);
        redirectToHome();
    })
        .fail(function (err) {
            $("#order-button").attr("disabled", false);
            alert(err.responseJSON.message)
        });
}
