$(document).ready(function() {

    // add Handlebars.js into index.html and template
    //document location href
    //pass product as argument how?
    //another get with id or without
    //window location href

    //document location search to het product id for another ajax request
    //jquery can do it as well

    var source = $("#productTable").innerHTML;
    var template = Handlebars.compile(source);

	console.log("loaded");

	$("#search-button").click(function (event) {
	    // 1. take search string
	    var searchString = $(".form-control").val()
	    var data = {
	        title: searchString,
	        offset: 0,
	        limit: 5
        }
	    // 2. make get request
	    $.get("http://localhost:8080/app/products", data, function(data , status, jqXHR){
            // 3. display
            console.log(data);
            $.each(data, function(index, value){
                //$("#products").load("resources/products.html", data)
                //$(".card-title").append
                    $(".hidden-table").remove();
                   let productHtml = template(data);
                   $(".card-body").append(productHtml);
            });
            console.log(data)
	    })
	})
});