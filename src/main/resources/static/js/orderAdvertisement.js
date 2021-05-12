function sendOrder(){
    let additionalServices = [];
    let id = getParameter("id");

    //show error if parameter is empty or broken or etc
    $(".additionalService").each(function (){
        if(this.checked){
            additionalServices.push($(this).val());
        }
    })
    let order = {
        advertisementId: id,
        additionalServicesId: additionalServices
    }
    $.ajax({
        type: "POST",
        data: JSON.stringify(order),
        url: SERVER_URL + "/api/order/",
        contentType: "application/json"
    });
}
