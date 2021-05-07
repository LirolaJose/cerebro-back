$(function () {
function fillOrderFormInfo(id) {
    $.ajax({
        type: "GET",
        url: API_ADVERTISEMENT + id
    }).done(function (advertisement) {
        console.log(advertisement);
        $("#order-advertisement-title").append($("<a href=advertisement.html?id="
            + advertisement.id + ">" + advertisement.title + '</a>'));

        $("#order-advertisement-price").append("Price: " + advertisement.price)
            .attr("text", advertisement.price);

        $("#order-advertisement-image").append($("<img/>")
            .attr("src", "http://localhost:8080/api/image/" + id));

        if(advertisement.status === "ACTIVE") {
            //fixme do you need this check if it was checked near order button? if yes - do we need to add check if category is orderable?
            $("#confirm-order").append($("<input/>")
                .attr("type", "submit")
                .attr("value", "CONFIRM THE ORDER")
                //fixme not sure but probably you have to leave sendOrder without parenthesis
                .attr("onclick", sendOrder()));
        }

        fillAdditionalServicesInfo(id);
    });
}

function fillAdditionalServicesInfo(id) {
    $.ajax({
        type: "GET",
        url: API_ADDITIONAL_SERVICES + "advertisement/" + id
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

const urlString = window.location.search;
//fixme
//fixme also do we need to fetch and check current user here only for case when someone just navigate here via bookmark?
let id = urlString.substring(urlString.lastIndexOf("=") + 1);
fillOrderFormInfo(id);
})
