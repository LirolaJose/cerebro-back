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
        fillAdditionalServicesInfo(id);
    });
}

function fillAdditionalServicesInfo(id) {
    $.ajax({
        type: "GET",
        url: API_ADDITIONAL_SERVICES + "advertisement/" + id
    }).done(function (additionalServicesList) {
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
let id = urlString.substring(urlString.lastIndexOf("=") + 1);
fillOrderFormInfo(id);
})
