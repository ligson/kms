/**
 * Created by ligson on 2015/4/17 0017.
 */
$(function () {
    $("#resetpwd_form").bootstrapValidator({
        message: '输入格式不正确！',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            password: {
                validators: {
                    notEmpty: {
                        message: '密码禁止为空！'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '密码长度是6-30位'
                    }
                }
            },
            password2: {
                validators: {
                    notEmpty: {
                        message: '密码禁止为空！'
                    },
                    identical: {
                        field: "password",
                        message: "两次输入不一致!"
                    }
                }
            }
        }
    });
});