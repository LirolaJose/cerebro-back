$(function () {
    function getCurrentUser(callback) {
        $.ajax({
            type: "GET",
            url: CURRENT_USER
            //fixme what if current user will not be fetched? should we redirect to login page?
        }).done(function (resp) {
            console.log(resp);
            let user = resp.value;

            const urlString = window.location.search;
            //fixme advertisment fetch logic move to common and replace all usages
            let advertisementId = urlString.substring(urlString.lastIndexOf("=") + 1);

            getAdvertisementInfoById(advertisementId, user);
            getImagesList(advertisementId);
            getAdditionalServicesByAdvertisementId(advertisementId);

            // fixme move to common
            // callback(user);
        });
    }

    // getCurrentUser(function (user) {
    //         let advertisementId = urlString.substring(urlString.lastIndexOf("=") + 1);
    //         getAdvertisementInfoById(advertisementId, user);
    //         getImagesList(advertisementId);
    //         getAdditionalServicesByAdvertisementId(advertisementId);
    //     }
    // );

    function getAdvertisementInfoById(advertisementId, user) {
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

            if (user.email !== null ) {
                if (advertisement.category.orderable === true && advertisement.status === "ACTIVE") {
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
            }
        });
    }

    function getImagesList(advertisementId) {
        $.ajax({
            type: "GET",
            url: API_IMAGE + "imagesList/" + advertisementId
        }).done(function (imagesList) {
            console.log(imagesList);
            if (imagesList.length === 0) {
                $("#images")
                    .append($("<img/>")
                        .attr("src", "/image/notFound.JPG"));
            } else {
                $.each(imagesList, function (index, image) {
                    $("#images")
                        .append($("<img/>")
                            .attr("src", API_ADVERTISEMENT + "image/" + image.id));

                });
            }
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

    getCurrentUser();
})


