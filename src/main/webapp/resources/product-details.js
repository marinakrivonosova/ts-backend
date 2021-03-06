$(document).ready(function() {
    $("#after-button").hide();

    const template = Handlebars.compile($("#detailed-product-template").html());

    const keyStorage = "my-cart";

    function addToCart(product) {
        let newCart = JSON.parse(localStorage.getItem(keyStorage)) || {};

        let productCounter = newCart[product.productId];

        if (productCounter) {
            productCounter.count++;
        } else {
            productCounter = {
                count: 1,
                product: product
            };
        }
        newCart[product.productId] = productCounter;
        localStorage.setItem(keyStorage, JSON.stringify(newCart));
    }

    let productId = new URLSearchParams(location.search).get("product-id");

    $.get(apiPath + `/products/${productId}`, function(data, status, jqXHR) {
        let context = {
            title: data.name,
            price: data.price,
            description: data.description,
            weight: data.weight,
            volume: data.volume
        };
        let html = template(context);
        $("#product-container").append(html);
        $("#add-to-card-btn").click(function(event) {
            addToCart(data);
            $("#after-button").show();
        });
    });
});