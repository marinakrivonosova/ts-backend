$(document).ready(function () {
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
                localStorage.setItem("token", response)
                $(location).attr("href", apiPath + "admin/admin-page.html");
            },
            error: function (jqXHR, status, errorThrown) {
                alert("Invalid username/password supplied!")
            }
        });
    });
});