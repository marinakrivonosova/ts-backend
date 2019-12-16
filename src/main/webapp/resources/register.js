$(document).ready(function () {
    $("#register-alert").hide();
    $("#registerForm").validate({
        submitHandler: function (form, event) {
            const data = {
                firstName: $("#inputName").val(),
                lastName: $("#inputLastName").val(),
                birthDate: $("#inputBirthday").val(),
                password: $("#inputPassword").val(),
                email: $("#inputEmail").val(),
                phone: $("#inputPhone").val()
            };
            $.ajax({
                type: "POST",
                url: apiPath + "/users/register",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                success: function (response, status, jqXHR) {
                    $(location).attr("href", rootPath + "/products.html");
                },
                error: function (jqXHR, status, errorThrown) {
                    $("#register-alert").show();
                }
            });
        },
        rules: {
            name: {
                required: true
            },
            lastName: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                regx: "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%]){7,20}",
                minlength: 8,
                maxlength: 20
            },
            repeatedPassword: {
                required: true,
                equalTo: "#inputPassword"
            },
            phone: {
                required: true,
                regxPhone: "^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]*$"
            },
            birthday: {
                required: true
            }
        },
        messages: {
            name: "Please enter your firstname.",
            lastName: "Please enter your lastname.",
            password: {
                required: "Please enter a password",
                minLength: "Your password must be at least 8 characters long.",
                maxLength: "Your password must be not more then 20 characters long.",
            },
            email: "Please enter a valid email address.",
            phone: "Please enter a phone number.",
            birthday: "Please enter a date of birth.",
            repeatedPassword: "Passwords do not match."
        }
    });

    $.validator.addMethod("regx", function (value, element, regexpr) {
        let re = new RegExp(regexpr);
        return this.optional(element) || re.test(value);
    });
    $.validator.addMethod("regxPhone", function (value, element, regexpres) {
        let re = new RegExp(regexpres);
        return this.optional(element) || re.test(value);
    });
});