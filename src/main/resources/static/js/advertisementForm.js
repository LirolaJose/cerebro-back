function createAdvertisement() {
    let checkboxes = [];
    $(".additionalService").each(function (){
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
        additionalServicesId: checkboxes
    }
    let data = new FormData();
    data.append("advertisementDTO", new Blob([JSON.stringify(advertisement)], {type: "application/json"}));

    let allImages = document.getElementById("image").files.length;
    for (let index = 0; index < allImages; index ++){
        let maxSize = 51200;
        if(document.getElementById("image").files[index].size > maxSize){
            alert("Image's size is more than accepted value (50MB). This image will not be uploaded")
        }
        data.append("images", document.getElementById("image").files[index])
    }

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        data: data,
        url: API_ADVERTISEMENT +"/",
        processData: false,
        contentType: false
    });
}




