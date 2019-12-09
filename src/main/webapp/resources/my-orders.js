$(document).ready(function () {
    console.log("loaded");
    const orderTemplate = Handlebars.compile($("#order-template").html());
    const orderStatusTemplate = Handlebars.compile($("#dropdown-status-template").html());

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
            headers: {"Authorization": "Bearer " + localStorage.getItem('token')},
            contentType: "application/json; charset=utf-8",
            success: function (orders, status, jqXHR) {
                $("#order-list").children().remove();
                $.each(orders, function (index, order) {
                    const orderTemplateHtml = $(orderTemplate(order));
                    $("#order-list").append(orderTemplateHtml);

                });
            }
        })
    });
});