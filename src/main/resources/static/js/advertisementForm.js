function createAdvertisement() {
    let checkboxes = [];
    $(".cb").each(function (){
        if(this.checked){
            checkboxes.push($(this).val());
        }
    })
    let advertisement = {
        title: $("#title").val(),
        text: $("#text").val(),
        price: $("#price").val(),
        type: $("#selectType option:selected").val(),
        categoryId: $("#selectCategory option:selected").val(),
        additionalServices: checkboxes
    }
    $.ajax({
        type: "POST",
        data: JSON.stringify(advertisement),
        dataType: "json",
        url: "http://localhost:8080/api/advertisement/",
        contentType: "application/json"
    });
}




