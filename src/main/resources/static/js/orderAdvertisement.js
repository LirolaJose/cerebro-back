function sendOrder(){
    let checkboxes = [];
    const urlString = window.location.search;
    let id = urlString.substring(urlString.lastIndexOf("=") + 1);

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
        url: "http://localhost:8080/api/order/",
        contentType: "application/json"
    });
}