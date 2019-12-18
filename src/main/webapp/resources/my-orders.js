$(document).ready(function () {
    $("#all-orders").hide();
    $("#login-alert").show();
    $("#order-status").hide();

    const orderTemplate = Handlebars.compile($("#dropdown-template-orders").html());
    const orderStatusTemplate = Handlebars.compile($("#dropdown-status-template").html());

    $.get(apiPath + "/get-cookies", function (cookie, status, jqXHR) {
        $("#order-status").show();
        $("#login-alert").hide();
    });

    $.get(apiPath + "/order-statuses", function (orderStatuses, status, jqXHR) {
        $.each(orderStatuses, function (index, status) {
            const orderStatusHtml = $(orderStatusTemplate(status));
            $("#order-status").append(orderStatusHtml);
        });
    });
    $("#order-status").change(function () {
        $.ajax({
            type: "GET",
            url: apiPath + `/orders`,
            data: {
                status: $(this).val()
            },
            contentType: "application/json; charset=utf-8",
            success: function (orders, status, jqXHR) {
                $("#order-list").children().remove();
                $("#all-orders").show();
                $("#table-body").children().remove();
                $.each(orders, function (index, order) {
                    $.ajax({
                        type: "GET",
                        url: apiPath + `/delivery-methods/${order.deliveryMethodId}`,
                        contentType: "application/json; charset=utf-8",
                        success: function (deliveryMethod, status, jqXHR) {
                            $.ajax({
                                type: "GET",
                                url: apiPath + `/payment-statuses/${order.paymentStatusId}`,
                                contentType: "application/json; charset=utf-8",
                                success: function (paymentStatus, status, jqXHR) {
                                    let context = {
                                        id: order.id,
                                        orderNumber: order.orderNumber,
                                        address: order.address,
                                        deliveryMethod: deliveryMethod.deliveryMethod,
                                        paymentStatus: paymentStatus.paymentStatus
                                    };
                                    const orderTemplateHtml = $(orderTemplate(context));
                                    $("#table-body").append(orderTemplateHtml);
                                }
                            });
                        }
                    });
                });
            }
        });
    });
});