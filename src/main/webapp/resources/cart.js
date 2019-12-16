$(document).ready(function () {

    $("#address-form").validate({
        rules: {
            city: {
                required: true
            },
            address: {
                required: true
            },
            zip: {
                required: true
            }
        },
        messages: {
            city: "Please fill the form",
            address: "Please fill the form",
            zip: "Please fill the form"
        }
    });
    const lineItemTemplate = Handlebars.compile($("#line-item-template").html());
    const paymentMethodTemplate = Handlebars.compile($("#dropdown-template-payment").html());
    const deliveryMethodTemplate = Handlebars.compile($("#dropdown-template-delivery").html());

    const keyStorage = "my-cart";
    const cart = localStorage.getItem(keyStorage);

    if (cart) {
        $("#cart-empty-badge").hide();
        $("#order-succeeded").hide();

    } else {
        $("#product-table").hide();
        $("#additional-info").hide();
        $("#order-succeeded").hide();
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
    $("#total-price").append($('<span />').html(`$${totalPrice}`));

    $.get(apiPath + "/payment-methods", function (paymentMethods, status, jqXHR) {
        $.each(paymentMethods, function (index, method) {
            const paymentMethodHtml = $(paymentMethodTemplate(method));
            $("#payment-method").append(paymentMethodHtml);
        });
    });
    $.get(apiPath + "/delivery-methods", function (deliveryMethods, status, jqXHR) {
        $.each(deliveryMethods, function (index, method) {
            const deliveryMethodHtml = $(deliveryMethodTemplate(method));
            $("#delivery-method").append(deliveryMethodHtml);
        });
    });


    $("#submit-order").click(function (event) {
        if ($("#address-form").valid()) {
            const address = $("#zip").val() + " " + $("#city").val() + " " + $("#address").val();
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
                url: apiPath + "/orders",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                success: function (response, status, jqXHR) {
                    $("#order-succeeded").show();
                    localStorage.removeItem(keyStorage);
                    $("#product-table").hide();
                    $("#additional-info").hide();
                },
                error: function (jqXHR, status, errorThrown) {
                    $("#card-body").append(`<div class="alert alert-primary" role="alert">
                    For submitting an order you need to <a href="login.html" class="alert-link">login</a></div>`);
                }
            });
        }
    });
});