$(function () {
    if (typeof(Worker) == "undefined") {
        $(".login-container").hide();
        $(".ui-mask-box").show();
    }

    $("form").submit(function () {
        var username = $("#username").val() + "";
        var password = $("#password").val() + "";
        if (username.isEmpty() || password.isEmpty()) {
            $("#errorMsg").empty().append("用户名和密码不允许为空!").show();
            return false;
        }else{
            $("#errorMsg").hide();
        }
    });
});

