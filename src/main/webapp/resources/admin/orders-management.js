$(document).ready(function () {
    const tableTemplate = Handlebars.compile($("#table-template").html());

    const paymentMethodTemplate = Handlebars.compile($("#dropdown-template-payment").html());
    const deliveryMethodTemplate = Handlebars.compile($("#dropdown-template-delivery").html());
});