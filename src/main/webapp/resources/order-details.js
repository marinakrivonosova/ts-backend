$(document).ready(function () {
    const lineItemTemplate = Handlebars.compile($("#template-line-item").html());

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
        });
        $("#totalPrice").append($('<span />').html(`$${totalPrice}`));
    });
});