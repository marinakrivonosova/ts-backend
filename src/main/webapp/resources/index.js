$(document).ready(function() {
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
            console.log(data)
            $.each(data, function(index, value){
                $("#products").load("resources/products.html", data)
                $(".card-title").append

            });
            console.log(data)
	    })
	})

//	$("#search-button").click(){
//	    console.log('btn clicked');
//    	console.log(event);
//
//    	$.ajax({
//    	method: 'GET',
//    	url: 'http://localhost:8080/app/products,
//    						data: {title, offset, limit}
//    					}).done(function(data) {
//    						console.log(data);
//    						searchProducts(data);
//    					  })
//    					  .fail(function(err) {
//    						console.log(err);
//    					  })
//    					  .always(function() {
//    					  });
//	}

});