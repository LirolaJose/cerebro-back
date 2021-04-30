$(function () {
    function getCurrentUser() {
        $.ajax({
            type: "GET",
            url: CURRENT_USER
        }).done(function (resp) {
            console.log(resp);

            let user = resp.value;
            showActiveAdvertisements(user)
        });
    }

    function showActiveAdvertisements(user) {
        $.ajax({
            type: "GET",
            url: API_ADVERTISEMENT
        }).done(function (advertisementsList) {
            console.log(advertisementsList);
            if(user === null){
                $("#log-in-button").append($("<input/>")
                    .attr("type", "button")
                    .attr("value", "LOGIN")
                    .attr("onclick", "location.href = LOGIN"));

                $(".registration-button-form").append($("<input/>")
                    .attr("type", "submit")
                    .attr("value", "REGISTRATION"));
            }else{
                $("#log-out-button").append($("<input/>")
                    .attr("type", "button")
                    .attr("value", "LOGOUT")
                    .attr("onclick", "location.href = LOGOUT"));

                $(".new-advertisement-button-form").append($("<input/>")
                    .attr("type", "submit")
                    .attr("value", "NEW ADVERTISEMENT"));
            }

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

                table += '<td>' + '<a href="advertisement.html?id=' + advertisement.id + '">' + advertisement.title + '</a>' + '</td>';

                table += '<td>' + advertisement.text + '</td>';
                table += '<td>' + advertisement.price + '</td>';
                table += '<td>' + '<img src="http://localhost:8080/api/image/' + advertisement.id + '"/>' + '</td>';
                table += '<td>' + advertisement.type + '</td>';
                table += '<td>' + advertisement.category.name + '</td>';
                table += '<td>' + advertisement.owner.firstName + " " + advertisement.owner.secondName + '</td>';
                table += '</tr>'
            });
            table += "</table>"
            $('#table').append(table)

        }).fail(function () {
            $('#table').html("<p>Error!</p>");
        });
    }
    getCurrentUser();
});

