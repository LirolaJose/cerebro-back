function createAdvertisement() {
    $("#button-submit").attr("disabled", true);
    let checkboxes = [];
    $(".additionalService").each(function () {
        if (this.checked) {
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
    for (let index = 0; index < allImages; index++) {
        let maxSize = 5 * 1024 * 1024; // 5MB
        if (document.getElementById("image").files[index].size < maxSize) {
            data.append("images", document.getElementById("image").files[index])
        }
    }

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        data: data,
        beforeSend: function (xhr){
            setTokenToHeader(xhr)
        },
        url: API_ADVERTISEMENT + "/",
        processData: false,
        contentType: false
    })
        .done(function (resp) {
            console.log(resp);
            window.location.href = "advertisementsList.html";
        })
        .fail(function (err) {
            console.log(err);
            $("#button-submit").attr("disabled", false);
            alert(err.responseJSON.message)
        });
}




