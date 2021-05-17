$(function () {
    $("#selectType").change(function () {
        $("#additionalServices").empty()
    })

    $("#selectCategory").change(function () {
        let categoryId = $("#selectCategory option:selected").attr("value");
        $.ajax({
            type: "GET",
            beforeSend: function (xhr){
                setTokenToHeader(xhr)
            },
            url: API_ADDITIONAL_SERVICES + "/category/" + categoryId
        }).done(function (additionalServicesList) {
            console.log(additionalServicesList);
            if (additionalServicesList.length === 0) {
                $("#additionalServices").empty()
                return false;
            }
            $("#additionalServices").html('<label id="services">Additional services: <br></label>')
            $.each(additionalServicesList, function (index, additionalService) {
                $("#services").append($('<input class="additionalService" type="checkbox">' + additionalService.name + ", price: " +
                    + additionalService.price + '$' + '<br/>')
                    .attr("value", additionalService.id));
            });
        });
    });
});
