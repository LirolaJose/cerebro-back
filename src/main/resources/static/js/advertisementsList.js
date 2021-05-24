$(function () {
    function getCurrentUser() {
        $.ajax({
            type: "GET",
            beforeSend: function (xhr){
                setTokenToHeader(xhr)
            },
            url: CURRENT_USER + "/"
        }).done(function (resp) {
            console.log(resp);

            let userInfo = resp.value;
            if (userInfo === null) {
                $("#login-button-form").append($("<input/>")
                    .attr("type", "submit")
                    .attr("value", "LOGIN"));

                $("#registration-button-form").append($("<input/>")
                    .attr("type", "submit")
                    .attr("value", "REGISTRATION"));
            } else {
                $("#logout-button").append($("<input onclick='logoutUser()'/>")
                    .attr("type", "button")
                    .attr("value", "LOGOUT"));

                $("#new-advertisement-button-form").append($("<input/>")
                    .attr("type", "submit")
                    .attr("value", "NEW ADVERTISEMENT"));

                $("#user-money-menu")
                    .append($("<p></p>").text("Logged user: " + userInfo.email))
                    .append($("<p></p>").text("Money: " + userInfo.moneyAmount + "$"));

                $("#user-money-menu").append($("<div id='add-money-form'></div>"));
                $("#add-money-form").append($("<div id='money-form-info'>"));
                $("#money-form-info").append($("<input id='entered-money' >")
                    .attr("type", "number")
                    .attr("min", 1)
                    .attr("step", 1)
                    .attr("placeholder", "enter amount"));

                $("#money-form-info").append($("<input type='button' >")
                    .attr("value", "TOP UP THE BALANCE").click(function () {
                        addMoney(userInfo.id, $("#entered-money").val());
                    }));
            }
            showActiveAdvertisements()
        });

    }

    function showActiveAdvertisements() {
        $.ajax({
            type: "GET",
           beforeSend: function (xhr){
                setTokenToHeader(xhr)
            },
            url: API_ADVERTISEMENT + "/"
        }).done(function (advertisementsList) {
            console.log(advertisementsList);
            let table;
            if (advertisementsList.length !== 0) {
                $.each(advertisementsList, function (index, advertisement) {
                    table += '<tr>'
                    table += '<td>' + advertisement.id + '</td>';
                    table += '<td>' + '<a href="advertisement.html?id=' + advertisement.id + '">' + advertisement.title + '</a>' + '</td>';
                    table += '<td>' + advertisement.text + '</td>';
                    table += '<td>' + advertisement.price + "$" +'</td>';
                    table += '<td>' + '<img src="' + API_IMAGE + "/" + advertisement.id + '"/>' + '</td>';
                    table += '<td>' + advertisement.type.name + '</td>';
                    table += '<td>' + advertisement.category.name + '</td>';
                    table += '<td>' + advertisement.owner.firstName + " " + advertisement.owner.secondName + '</td>';
                    table += '</tr>'
                });
            } else {
                $("#advertisements-list-table").append('<i><p id="text" align="center"></p></i>');
                $("#text").text("advertisements not found");
            }

            $('#table').append(table);
        }).fail(function () {
            $('#table').html("<p>Error!</p>");
        });
    }

    getCurrentUser();
})
;

