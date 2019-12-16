$(document).ready(function () {
    $("#login-alert").hide();
    $("#loginButton").click(function (event) {
        const login = $("#email").val();
        const password = $("#password").val();
        const data = {
            login: login,
            password: password
        };
        $.ajax({
            type: "POST",
            url: apiPath + "/users/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            success: function (response, status, jqXHR) {
                let path;
                for (let i = 0; i < response.length; i++) {
                    if (response[i] === "ADMIN") {
                        path = rootPath + "/admin/admin-page.html";
                        break;
                    } else {
                        path = rootPath + "/products.html";
                    }
                }
                $(location).attr("href", path);
            },
            error: function (jqXHR, status, errorThrown) {
                $("#login-alert").show();
            }
        });
    });
});