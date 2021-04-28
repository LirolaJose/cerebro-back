$(function () {
    function getAdvertisement(id) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/advertisement/" + id
        }).done(function (advertisement) {
            console.log(advertisement);
            $("#title-info").append(advertisement.title);
            $("#textArea").append(advertisement.text);
            $("#price").append(advertisement.price);
            $("#additional-service").append(advertisement.additionalServices);
            $("#type").append(advertisement.type);
            $("#category").append(advertisement.category.name);
            $("#owner").append(advertisement.owner.firstName + " " + advertisement.owner.secondName);
        });
    }

    function getImagesList(id) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/imagesList/" + id
        }).done(function (imagesList) {
            console.log(imagesList);
            $.each(imagesList, function (index, image) {
                $("#images")
                    .append($("<img/>")
                    .attr("src", "http://localhost:8080/imageIM/" + image.id));
            });
        });
    }

    function getAdditionalAdvertisements(id){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/additionalServices/advertisement/" + id
        }).done(function (additionalServicesList){
            console.log(additionalServicesList);
            $.each(additionalServicesList, function(index, additionalService){
                $("#additional-services").append($("<li></li>")
                    .text(additionalService.name + ", price: " + additionalService.price));
            })
        })
    }

    const queryString = window.location.search;
    let id = queryString.substring(queryString.lastIndexOf("=") + 1);
    getAdvertisement(id);
    getImagesList(id);
    getAdditionalAdvertisements(id);
})

// function getParameterByName(name) {
//     const match = RegExp(`[?&]${name}=([^&]*)`).exec(window.location.search);
//     return match && decodeURIComponent(match[1].replace(/\+/g, ' ') );
// }

