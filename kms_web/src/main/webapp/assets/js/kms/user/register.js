/**
 * Created by ligson on 2015/4/17 0017.
 * 参考文档http://www.jq22.com/yanshi522
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
            name: {
                validators: {
                    notEmpty: {
                        message: '该项不能为空!'
                    },
                    remote:{
                        url:"/ra/user/checkLoginName.json",
                        message:"用户名已存在"
                    }
                }
            },
            mobile: {
                validators: {
                    notEmpty: {
                        message: '手机号禁止为空！'
                    }, regexp: {
                        regexp: /^1\d{10}$/,
                        message: "请输入正确的手机号!"
                    },
                    remote:{
                        url:"/ra/user/checkLoginName.json",
                        message:"手机号已存在"
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
                    }
                },
                remote:{
                    url:"/ra/user/checkLoginName.json",
                    message:"邮箱已存在"
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
            },
            password2: {
                validators: {
                    /*different: {
                     field: "password",
                     message: "两次输入密码不一致！"
                     },*/
                    notEmpty: {
                        message: '确认密码禁止为空！'
                    },
                    identical: {
                        field: 'password',
                        message: '两次输入密码不一致！'
                    }
                }
            }

        }
    });
});