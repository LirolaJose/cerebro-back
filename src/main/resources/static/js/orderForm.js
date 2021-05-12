$(function () {
    function fillOrderFormInfo(advertisementId) {
        $.ajax({
            type: "GET",
            url: API_ADVERTISEMENT +"/" + advertisementId
        }).done(function (advertisement) {
            console.log(advertisement);
            $("#order-advertisement-title").append($("<a href=advertisement.html?id="
                + advertisement.id + ">" + advertisement.title + '</a>'));

            $("#order-advertisement-price").append("Price: " + advertisement.price)
                .attr("text", advertisement.price);

            $("#order-advertisement-image").append($("<img/>")
                .attr("src", API_IMAGE + "/" + advertisementId));

            if (advertisement.category.orderable === true && advertisement.status === "ACTIVE") {
                $("#confirm-order").append($("<input onclick='sendOrder()'/>")
                    .attr("type", "submit")
                    .attr("value", "CONFIRM THE ORDER"));
            }

            fillAdditionalServicesInfo(advertisementId);
        });
    }

    function fillAdditionalServicesInfo(id) {
        $.ajax({
            type: "GET",
            url: API_ADDITIONAL_SERVICES + "/advertisement/" + id
        }).done(function (additionalServicesList) {
            //fixme here's additional services logic in the other place, should we combine them or not?
            console.log(additionalServicesList);
            $.each(additionalServicesList, function (index, additionalService) {
                $("#order-additional-services").append($('<input class="additionalService" type="checkbox">' + additionalService.name + ", price: " +
                    +additionalService.price + '<br/>')
                    .attr("value", additionalService.id)
                    .attr("text", additionalService.price));
            });
            getTotal();
        });
    }
//fixme also do we need to fetch and check current user here only for case when someone just navigate here via bookmark?
    let advertisementId = getUrlParameter("id");
    fillOrderFormInfo(advertisementId);
})
