$(document).ready(function (){
    console.log("loaded")

    var cartRowTemplate = Handlebars.compile($("#cart-template").html())

    for(let i = 1; i <= localStorage.length; i++) {
        var context = {
            product: localStorage.key(i)
                }
        var product =cartRowTemplate(context)
                    $("#cart-row").append(product)
                    console.log(product)
    }
})