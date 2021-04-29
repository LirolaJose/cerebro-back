$(function () {
    function fillTypesSelect() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/types"
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

