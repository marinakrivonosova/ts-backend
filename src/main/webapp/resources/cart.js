$(document).ready(function () {
    const lineItemTemplate = Handlebars.compile($("#line-item-template").html());
    const paymentMethodTemplate = Handlebars.compile($("#dropdown-template").html());

    const keyStorage = "my-cart";
    const cart = localStorage.getItem(keyStorage);

    if (cart) {
        $("#cart-empty-badge").hide();

    } else {
        $("#product-table").hide();
        $("#additional-info").hide();
    }

    let totalPrice = 0;
    let rowNumber = 0;
    let lineItems = $.map(JSON.parse(cart), function (value, index) {
        const lineItemHtml = lineItemTemplate({
            rowNumber: ++rowNumber,
            productName: value.product.name,
            count: value.count,
            price: value.product.price * value.count
        });

        $("#cart-items").append(lineItemHtml);

        totalPrice += value.product.price * value.count;
        return {
            productId: index,
            count: value.count
        };
    });
    $("#total-price").append(`<span>$</span>${totalPrice}`);

    $.get("/app/payment-methods", function (paymentMethods, status, jqXHR) {
        $.each(paymentMethods, function (index, method) {
            const paymentMethodHtml = $(paymentMethodTemplate(method));
            $("#payment-method").append(paymentMethodHtml);
        });
    });

    $("#submit-order").click(function (event) {
        const address = $("#zip").val() + $("#city").val() + $("#address").val();
        //TODO change to dynamic when back end logic is ready
        const deliveryMethodId = $("#delivery-method").val();
        const paymentMethodId = $("#payment-method").val();
        const data = {
            address: address,
            deliveryMethodId: deliveryMethodId,
            paymentMethodId: paymentMethodId,
            lineItemList: lineItems
        };
        $.ajax({
            type: "POST",
            url: "/app/orders",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            success: function (response, status, jqXHR) {
                console.log(response);
                localStorage.clear();
            }
        });
    });
});