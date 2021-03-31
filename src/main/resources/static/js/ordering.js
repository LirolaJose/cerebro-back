$(function () {

    function getTotal() {

        var total = parseFloat($("#advertisementPrice").text());
        var selector = ".totalPrice:checked";
        $(selector).each(function () {
            total += parseFloat($(this).attr("amount"));
        });
        $('#total').val(total);
    }
    $(".totalPrice").click(function (event) {
        getTotal();
    });
    getTotal();
});
