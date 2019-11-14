$(document).ready(function (){
    console.log("loaded")

    for(let i = 1; i <= localStorage.length; i++) {
        var context = {
            product: localStorage.key(i)
            }
    }
})