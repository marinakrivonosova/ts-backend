$(document).ready(function() {
    console.log("loaded");

    var source = $("#detailed-product-template").html();
    var template = Handlebars.compile(source);

    var productId = new URLSearchParams(location.search).get("product-id")

    $.get(`http://localhost:8080/app/products/${productId}`, function(data, status, jqXHR) {
        var context = {
            title: data.name,
            price: data.price,
            description: data.description,
            weight: data.weight,
            volume: data.volume
        }
        var html = template(context)
        $("#product-container").append(html)
    })
})