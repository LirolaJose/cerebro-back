$(function () {
    function showActiveAdvertisements() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/advertisement/"
        }).done(function (advertisementsList) {
            console.log(advertisementsList);

            let table = "<table class=\"table\">\n" +
                "<tr>\n" +
                "<th>№</th>\n" +
                "<th>Title</th>\n" +
                "<th>Text</th>\n" +
                "<th>Price</th>\n" +
                "<th>Image</th>\n" +
                "<th>Type</th>\n" +
                "<th>Category</th>\n" +
                "<th>Owner</th>\n" +
                "</tr>";

            $.each(advertisementsList, function (index, advertisement) {
                table += '<tr>'
                table += '<td>' + advertisement.id + '</td>';

                table += '<td>' + '<a href="http://localhost:63342/cerebro/static/advertisement.html?id=' + advertisement.id +  '">' +  advertisement.title + '</a>' + '</td>';

                table += '<td>' + advertisement.text + '</td>';
                table += '<td>' + advertisement.price + '</td>';
                table += '<td>' + '<img src="http://localhost:8080/image/' + advertisement.id + '"/>' + '</td>';
                table += '<td>' + advertisement.type + '</td>';
                table += '<td>' + advertisement.category.name + '</td>';
                table += '<td>' + advertisement.owner.firstName + " " + advertisement.owner.secondName + '</td>';
                table += '</tr>'
            })
            table += "</table>"
            $('#table').append(table)
            // $.each(advertisementsList, function (index, advertisement) {
            //     getMainImage(advertisement.id);
            // })
        }).fail(function (err) {
            $('#table').html("<p>Error!</p>");
        });
    }
    showActiveAdvertisements()
});
