$(document).ready(function () {
    const ordersTemplate = Handlebars.compile($("#dropdown-template-orders").html());

    $.get(apiPath + "/orders/all", function (orders, status, jqXHR) {
        $.get(apiPath + "/order-statuses", function (orderStatuses, status, jqXHR) {
            $.each(orders, function (index, order) {
                $.ajax({
                    type: "GET",
                    url: apiPath + `/users/${order.userId}`,
                    contentType: "application/json; charset=utf-8",
                    success: function (user, status, jqXHR) {
                        $.get(apiPath + "/order-statuses", function (orderStatuses, status, jqXHR) {
                            $.ajax({
                                type: "GET",
                                url: apiPath + `/delivery-methods/${order.deliveryMethodId}`,
                                contentType: "application/json; charset=utf-8",
                                success: function (deliveryMethod, status, jqXHR) {
                                    $.ajax({
                                        type: "GET",
                                        url: apiPath + `/payment-methods/${order.paymentMethodId}`,
                                        contentType: "application/json; charset=utf-8",
                                        success: function (paymentMethod, status, jqXHR) {
                                            $.ajax({
                                                type: "GET",
                                                url: apiPath + `/payment-statuses/${order.paymentStatusId}`,
                                                contentType: "application/json; charset=utf-8",
                                                success: function (paymentStatus, status, jqXHR) {
                                                    let orderStatusesWithSelection = [];
                                                    for (let i = 0; i < orderStatuses.length; i++) {
                                                        orderStatusesWithSelection.push({
                                                            id: orderStatuses[i].id,
                                                            orderStatus: orderStatuses[i].orderStatus,
                                                            selected: orderStatuses[i].id === order.orderStatusId
                                                        })
                                                    }

                                                    let context = {
                                                        id: order.id,
                                                        user: user,
                                                        deliveryMethod: deliveryMethod.deliveryMethod,
                                                        paymentMethod: paymentMethod.paymentMethod,
                                                        orderStatuses: orderStatusesWithSelection,
                                                        paymentStatus: paymentStatus.paymentStatus
                                                    };
                                                    const ordersHtml = $(ordersTemplate(context));
                                                    ordersHtml.find(".order-status-selector").change(function (event) {
                                                        const orderStatusId = $(this).val();
                                                        $.ajax({
                                                            type: "PUT",
                                                            url: apiPath + `/orders/${order.id}`,
                                                            data: JSON.stringify({
                                                                orderStatusId: orderStatusId
                                                            }),
                                                            contentType: "application/json; charset=utf-8",
                                                            success: function (orders, status, jqXHR) {
                                                            }
                                                        });
                                                    });
                                                    $("#table-body").append(ordersHtml);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        });
                    }
                });
            });
        });
    });
});