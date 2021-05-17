$(function () {
    $("#selectType").change(function () {
        let type = $("#selectType option:selected").attr("value");
        $.ajax({
            type: "GET",
            beforeSend: function (xhr){
                setTokenToHeader(xhr)
            },
            url:CATEGORIES +"/" + type
        }).done(function (categoriesList) {
            console.log(categoriesList);
            $("#selectCategory").html('<select id="selectCategory">' +
                ' <option disabled selected> Please, choose the category</option> </select>');
            $.each(categoriesList, function (index, category) {
                $("#selectCategory").append($("<option></option>")
                    .attr("value", category.id)
                    .text(category.name));
            });
        });
    });
});
