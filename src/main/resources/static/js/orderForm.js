$(function () {
    function fillOrderFormInfo(advertisementId) {
        $.ajax({
            type: "GET",
           beforeSend: function (xhr){
                setTokenToHeader(xhr)
            },
            url: API_ADVERTISEMENT +"/" + advertisementId
        }).done(function (advertisement) {
            console.log(advertisement);
            $("#order-advertisement-title").append($("<a href=advertisement.html?id="
                + advertisement.id + ">" + advertisement.title + '</a>'));

            $("#order-advertisement-price").append("Price: " + advertisement.price + "$")
                .attr("text", advertisement.price + "$");

            $("#order-advertisement-image").append($("<img/>")
                .attr("src", API_IMAGE + "/" + advertisementId));

            if (advertisement.type !== "BUY" && advertisement.category.orderable === true && advertisement.status === "ACTIVE") {
                $("#confirm-order").append($("<input id='order-button' onclick='sendOrder()'/>")
                    .attr("type", "submit")
                    .attr("value", "CONFIRM THE ORDER"));
            }

            fillAdditionalServicesInfo(advertisementId);
        });
    }

    function fillAdditionalServicesInfo(id) {
        $.ajax({
            type: "GET",
           beforeSend: function (xhr){
                setTokenToHeader(xhr)
            },
            url: API_ADDITIONAL_SERVICES + "/advertisement/" + id
        }).done(function (additionalServicesList) {
            console.log(additionalServicesList);
            $.each(additionalServicesList, function (index, additionalService) {
                $("#order-additional-services").append($('<input class="additionalService" type="checkbox">' + additionalService.name + ", price: " +
                    +additionalService.price + "$" + '<br/>')
                    .attr("value", additionalService.id)
                    .attr("text", additionalService.price));
            });
            getTotal();
        });
    }
    let advertisementId = getParameter("id");
    if(!advertisementId) {
        redirectToHome();
    }
    fillOrderFormInfo(advertisementId);
})
