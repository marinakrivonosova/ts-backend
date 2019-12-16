$(document).ready(function () {
    const lineItemTemplate = Handlebars.compile($("#template-line-item").html());

    $("#cancellation-alert").hide();
    $("#cancel-button").hide();

    let orderId = new URLSearchParams(location.search).get("order-id");
    let totalPrice = 0;
    $.get(apiPath + `/${orderId}/line-items`, function (lineItems, status, jqXHR) {
        $.each(lineItems, function (index, lineItem) {
            $.get(apiPath + `/products/${lineItem.productId}`, function (product, status, jqXHR) {
                let context = {
                    productId: product.productId,
                    name: product.name,
                    price: product.price,
                    count: lineItem.count
                };
                let lineItemHtml = lineItemTemplate(context);
                $("#all-products").append(lineItemHtml);
            });
            totalPrice += (lineItem.price - 0) * lineItem.count;

            $.ajax({
                type: "GET",
                url: apiPath + `/orders/all/${orderId}`,
                contentType: "application/json; charset=utf-8",
                success: function (order, status, jqXHR) {
                    if (order.orderStatusId === "osId1") {
                        $("#cancel-button").show();
                        $("#cancel-button").click(function (event) {
                            $.ajax({
                                type: "PUT",
                                url: apiPath + `/orders/${orderId}`,
                                data: JSON.stringify({
                                    orderStatusId: "osId3"
                                }),
                                contentType: "application/json; charset=utf-8",
                                success: function (orders, status, jqXHR) {
                                    $("#cancellation-alert").show();
                                }
                            });
                        });
                    }
                }
            });
        });
        $("#totalPrice").append($('<span />').html(`$${totalPrice}`));
    });
});
