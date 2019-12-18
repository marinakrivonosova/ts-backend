$(document).ready(function () {
    $("#alert-card").hide();
    $("#alert-order").hide();
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
    $("#payment-form").validate({
        rules: {
            cardholder: {
                required: true,
            },
            cardnumber: {
                required: true,
                digits: true,
                minlength: 16,
                maxlength: 16
            },
            expiration: {
                required: true,
                minlength: 7,
                maxlength: 7

            },
            cvc: {
                required: true,
                digits: true,
                minlength: 3,
                maxlength: 3
            }
        },
        messages: {
            cardholder: "Enter the name as shown on credit card",
            cardnumber: "Enter a valid 16 digit card number",
            expiration: "Enter the expiration date",
            cvc: "Enter the 3-digit code on back",
        }
    });

    $.ajax({
        type: "GET",
        url: (apiPath + "/get-cookies"),
        contentType: "application/json; charset=utf-8",
        success: function (response, status, jqXHR) {
            $("#alert-card").hide();
        },
        error: function (jqXHR, status, errorThrown) {
            $("#alert-card").show();
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
        $("#clear-cart").hide();
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
    $("#clear-cart").click(function (event) {
        localStorage.removeItem(keyStorage);
        $("#product-table").hide();
        $("#additional-info").hide();
        $("#cart-empty-badge").show();
    });
    $("#submit-order").click(function (event) {
        if ($("#payment-form").valid() && $("#address-form").valid()) {
            const cardNumber = $("#cc-number").val();
            const cvc = $("#x_card_code").val();
            const expiration = $("#cc-exp").val();
            const cardHolder = $("#cc-name").val();

            const address = $("#zip").val() + " " + $("#city").val() + " " + $("#address").val();
            const deliveryMethodId = $("#delivery-method").val();
            const paymentMethodId = $("#payment-method").val();
            const data = {
                address: address,
                deliveryMethodId: deliveryMethodId,
                paymentMethodId: paymentMethodId,
                lineItemList: lineItems,
                paymentInformation: {
                    cardNumber: cardNumber,
                    cvc: cvc,
                    expirationDate: expiration,
                    cardHolder: cardHolder,
                }
            };
            // $.ajax({
            //     type: "POST",
            //     url: apiPath + "/pay",
            //     data: JSON.stringify(cardData),
            //     contentType: "application/json; charset=utf-8",
            //     success: function (response, status, jqXHR) {
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
                    $("#alert-order").show();
                }
            });

        }
    });
});