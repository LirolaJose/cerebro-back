function createAdvertisement(){
    let advertisement = {
        title: $("#title").val(),
        text: $("#text").val(),
        price: $("#price").val(),
        type: $("#selectType option:selected").val()
    }
}

function getTypesSelect() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/types"
    }).done(function (typesList) {
        console.log(typesList);
        $.each(typesList, function (index, type) {
            $("#selectType").append($("<option></option>")
                .attr("value", type)
                .text(type));
        })
    })
}

