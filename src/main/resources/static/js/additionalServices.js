$(function () {
    $("#selectType").change(function () {
        $("#additionalServices").empty()
    })

    $("#selectCategory").change(function () {
        let category = $("#selectCategory option:selected").attr("value");
        // let category = $("#selectCategory option:selected").attr("id");
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/additionalServices/" + category
        }).done(function (additionalServicesList) {
            console.log(additionalServicesList);
            if (additionalServicesList.length === 0) {
                $("#additionalServices").empty()
                return false;
            }
            $("#additionalServices").html('<label id="services">Additional services: <br></label>')
            $.each(additionalServicesList, function (index, additionalService) {
                $("#services").append($('<input class="additionalService" type="checkbox">' + additionalService.name + '<br/>')
                    .attr("value", additionalService.id));
            });
        });
    });
});
