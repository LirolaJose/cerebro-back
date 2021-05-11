function sendOrder(){
    let checkboxes = [];
    const urlString = window.location.search;

    //use common logic to fetch id
    let id = urlString.substring(urlString.lastIndexOf("=") + 1);

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
        url: SERVER_URL + "api/order/",
        contentType: "application/json"
    });
}
