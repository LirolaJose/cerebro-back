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
            "<th>Type</th>\n" +
            "<th>Category</th>\n" +
            "<th>Owner</th>\n" +
            "</tr>";

        $.each(advertisementsList, function (index, advertisement) {
            table+= '<tr>'
            table+= '<td>' + advertisement.id + '</td>';
            table+= '<td>' + advertisement.title + '</td>';
            table+= '<td>' + advertisement.text + '</td>';
            table+= '<td>' + advertisement.price + '</td>';
            table+= '<td>' + advertisement.type + '</td>';
            table+= '<td>' + advertisement.category.name + '</td>';
            table+= '<td>' + advertisement.owner.firstName + " " + advertisement.owner.secondName + '</td>';
            table+= '</tr>'
        })
        table += "</table>"
        $('#table').append(table)
    }).fail(function (err) {
        $('#ad').html("<p>Error!</p>");
    });
}