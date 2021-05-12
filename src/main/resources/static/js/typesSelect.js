$(function () {
    function fillTypesSelect() {
        $.ajax({
            type: "GET",
            url: TYPES + "/"
        }).done(function (typesList) {
            console.log(typesList);
            $.each(typesList, function (index, type) {
                $("#selectType").append($("<option></option>")
                    .attr("value", type)
                    .text(type));
            });
        });
    }
    fillTypesSelect()
})

