function sendOrder(){
    let checkboxes = [];
    let id = getUrlParameter("id");

    //show error if parameter is empty or broken or etc
    $(".additionalService").each(function (){
        if(this.checked){
            checkboxes.push($(this).val());
        }
    })
    let order = {
        advertisementId: id,
        additionalServicesId: checkboxes
    }
    $.ajax({
        type: "POST",
        data: JSON.stringify(order),
        url: SERVER_URL + "/api/order/",
        contentType: "application/json"
    });
}
