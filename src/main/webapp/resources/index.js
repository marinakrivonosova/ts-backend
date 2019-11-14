$(document).ready(function() {
    // add Handlebars.js into index.html and template
    //document location href
    //pass product as argument how?
    //another get with id or without
    //window location href

    //document location search to het product id for another ajax request
    //jquery can do it as well
    var productsListTemplate = Handlebars.compile($("#card-template").html())

    var limit = 10
    var keyStorage = "my-cart"

    function addToCart(product) {
        var newCart = JSON.parse(localStorage.getItem(keyStorage)) || []
        for (var i = 0; i < newCart.length) {
            if (newCart.find(product)){
               /write logic here
            }
            } else {
                newCart.push({
                product: product,
                count: 1
            })
        }
        localStorage.setItem(keyStorage, JSON.stringify(newCart))
    }

    // page is 1-indexed
    function showPage(activePage) {
        // 1. take search string
        var searchString = $(".form-control").val()
        var data = {
            title: searchString,
            offset: (activePage - 1) * limit,
            limit: limit
        }

        // 2. make get request
        $.get("http://localhost:8080/app/products", data, function(data, status, jqXHR) {
            $("#products").children().remove();

            $.each(data.products, function (index, product){
                var productHtml = $(productsListTemplate(product))

                var productsArr = [{}]
                productHtml.find("a.add-to-card").click(function(event){
                    addToCart(product)
                })

            // 3. display products
                $("#products").append(productHtml)
            })
                // 4. display pagination
            var totalPages = Math.ceil(data.overallSuitableProducts / limit);
            $("#pagination").children().remove()
            for (let page = 1; page <= totalPages; page++) {
                var pageLink
                if (page === activePage) {
                    pageLink = $(`
                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${page}<span class="sr-only">(current)</span></span>
                    </li>
                    `)
                } else {
                    pageLink = $(`<li class="page-item"><a class="page-link" href="#">${page}</a></li>`)
                    pageLink.click(function(event) {
                        showPage(page)
                    })
                }

                $("#pagination").append(pageLink)
            }
        })
    }

    $("#search-button").click(function(event) {
        showPage(1)
    })
});