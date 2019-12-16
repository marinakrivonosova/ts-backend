$(document).ready(function () {

    const productsListTemplate = Handlebars.compile($("#card-template").html());

    const limit = 6;
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

    // page is 1-indexed
    function showPage(activePage) {
        const searchString = $("#search-input").val();
        const data = {
            title: searchString,
            offset: (activePage - 1) * limit,
            limit: limit
        };

        $.get(apiPath + "/products", data, function (data, status, jqXHR) {
            $("#products").children().remove();

            $.each(data.products, function (index, product) {
                const productHtml = $(productsListTemplate(product));

                productHtml.find("a.add-to-cart").click(function (event) {
                    addToCart(product);
                });

                $("#products").append(productHtml);
            });
            const totalPages = Math.ceil(data.overallSuitableProducts / limit);
            $("#pagination").children().remove();
            for (let page = 1; page <= totalPages; page++) {
                let pageLink;
                if (page === activePage) {
                    pageLink = $(`
                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${page}<span class="sr-only">(current)</span></span>
                    </li>
                    `);
                } else {
                    pageLink = $('<li />').addClass('page-item').append($('<a />').addClass('page-link').html(`${page}`));
                    pageLink.click(function (event) {
                        showPage(page);
                    });
                }
                $("#pagination").append(pageLink);
            }
        });
    }

    $("#search-button").click(function (event) {
        showPage(1);
    });
    $('#search-input').on("keypress", function (event) {
        if (event.which === 13) {
            showPage(1);
        }
    });
});