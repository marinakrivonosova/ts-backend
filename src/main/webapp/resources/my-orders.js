$(document).ready(function () {
    console.log("loaded");
    const orderTemplate = Handlebars.compile($("#order-template").html());
    const orderStatusTemplate = Handlebars.compile($("#dropdown-status-template").html());

    $.get("/app/order-statuses", function (orderStatuses, status, jqXHR) {
        $.each(orderStatuses, function (index, status) {
            const orderStatusHtml = $(orderStatusTemplate(status));
            $("#order-status").append(orderStatusHtml);
        });
    });
    $("#order-status").change(function () {
        let request = {
            status: $(this).val()
        };
        $.get("/app/orders", request, function (orders, status, jqXHR) {
            $("#order-list").children().remove();
            $.each(orders, function (index, order) {
                const orderTemplateHtml = $(orderTemplate(order));
                $("#order-list").append(orderTemplateHtml);

            });
        });
    });
});