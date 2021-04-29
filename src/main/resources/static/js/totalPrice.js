    function getTotal() {
        let total = parseFloat($("#order-advertisement-price").attr("text"));
        let selector = ".additionalService:checked";
        $(selector).each(function () {
            total += parseFloat($(this).attr("text"));
        });
        $('#total').val(total);

        $(".additionalService").click(function (event) {
            getTotal();
        });
    }
