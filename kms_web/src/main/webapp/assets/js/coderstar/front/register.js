/**
 * Created by ligson on 2015/4/17 0017.
 */
$(function () {
    $("#register_form").bootstrapValidator({
        message: '输入格式不正确！',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            nickName: {
                validators: {
                    notEmpty: {
                        message: '该项不能为空!'
                    }
                }
            },
            cellphone: {
                validators: {
                    notEmpty: {
                        message: '手机号禁止为空！'
                    }, regexp: {
                        regexp: /^1\d{10}$/,
                        message: "请输入正确的手机号!"
                    },
                    remote: {
                        url: "/index/checkCellphoneExist",
                        type: "POST",
                        delay: 1000,
                        message: "手机号已经存在!"
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱禁止为空！'
                    }, regexp: {
                        regexp: /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/,
                        message: "请输入正确的邮箱地址!"
                    }, remote: {
                        url: "/index/checkEmailExist",
                        type: "POST",
                        delay: 1000,
                        message: "邮箱已经存在!"
                    }
                }
            },
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
            }
        }
    });
});