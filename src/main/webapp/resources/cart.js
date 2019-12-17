$(document).ready(function () {
    $("#pay-invoice").hide();
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
    $("#pay-invoice").validate({
        rules: {
            cardholder: {
                required: true,
            },
            cardnumber: {
                required: true,
                digits: true,
                creditcard: true
            },
            expiration: {
                required: true,
                minlength: 5,
                maxlength: 5

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
        $("#pay-invoice").show();
        if ($("#pay-invoice").valid()) {
            const cardNumber = $("#cc-number").val();
            const cvc = $("#x_card_code").val();
            const expiration = $("#cc-exp").val();
            const cardHolder = $("#cc-name").val();

            const data = {
                cardNumber: cardNumber,
                cvc: cvc,
                expirationDate: expiration,
                cardHolder: cardHolder,
                chargedAmount: totalPrice
            };
            $("#payment-button").click(function (event) {
                $.ajax({
                    type: "POST",
                    url: apiPath + "/pay",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (response, status, jqXHR) {
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
                    },
                    error: function (jqXHR, status, errorThrown) {
                        $("#card-body").append(`<div class="alert alert-primary" role="alert">
                    Payment failed</div>`);
                    }
                });
            });
        }
    });
});