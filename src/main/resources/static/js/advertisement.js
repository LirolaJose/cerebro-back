$(function () {
    function getAdvertisementInfoById(advertisementId) {
        $.ajax({
            type: "GET",
            url: API_ADVERTISEMENT + advertisementId
        }).done(function (advertisement) {
            console.log(advertisement);
            $("#title-info").append(advertisement.title);
            $("#textArea").append(advertisement.text);
            $("#price").append(advertisement.price);
            $("#additional-service").append(advertisement.additionalServices);
            $("#type").append(advertisement.type);
            $("#category").append(advertisement.category.name);
            $("#owner").append(advertisement.owner.firstName + " " + advertisement.owner.secondName);
            if (advertisement.category.orderable === true) {
                $("#order").append($("<form></form>")
                    .attr("id", "orderButtonForm")
                    .attr("action", "orderForm.html"));

                $("#orderButtonForm").append($("<input/>")
                    .attr("type", "hidden")
                    .attr("name", "id")
                    .attr("value", advertisementId))
                    .append($("<input/>")
                        .attr("type", "submit")
                        .attr("value", "ORDER"));
            }
        });
    }

    function getImagesList(advertisementId) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/image/imagesList/" + advertisementId
        }).done(function (imagesList) {
            console.log(imagesList);
            $.each(imagesList, function (index, image) {
                $("#images")
                    .append($("<img/>")
                        .attr("src", API_ADVERTISEMENT + "image/" + image.id));
            });
        });
    }

    function getAdditionalServicesByAdvertisementId(advertisementId) {
        $.ajax({
            type: "GET",
            url: API_ADDITIONAL_SERVICES + "advertisement/" + advertisementId
        }).done(function (additionalServicesList) {
            console.log(additionalServicesList);
            $.each(additionalServicesList, function (index, additionalService) {
                $("#additional-services").append($("<li></li>")
                    .text(additionalService.name + ", price: " + additionalService.price));
            })
        })
    }

    const urlString = window.location.search;
    let advertisementId = urlString.substring(urlString.lastIndexOf("=") + 1);
    getAdvertisementInfoById(advertisementId);
    getImagesList(advertisementId);
    getAdditionalServicesByAdvertisementId(advertisementId);
})

// function getParameterByName(name) {
//     const match = RegExp(`[?&]${name}=([^&]*)`).exec(window.location.search);
//     return match && decodeURIComponent(match[1].replace(/\+/g, ' ') );
// }

