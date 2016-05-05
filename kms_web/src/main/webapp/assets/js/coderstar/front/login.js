/**
 * Created by ligson on 2015/4/17 0017.
 */
$(function () {
    $("#login_form").bootstrapValidator({
        message: '输入格式不正确！',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '请输入邮箱地址或者手机号',
                validators: {
                    notEmpty: {
                        message: '该项不能为空!'
                    },
                    regexp: {
                        regexp: /(^1\d{10}$)|(^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$)/,
                        message: "请输入手机号或者邮箱地址!"
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码禁止为空！'
                    }
                }
            }
        }
    });
});
function qqLogin() {
    //以下为按钮点击事件的逻辑。注意这里要重新打开窗口
    //否则后面跳转到QQ登录，授权页面时会直接缩小当前浏览器的窗口，而不是打开新窗口
    var A = window.open("oauth/index.php", "TencentLogin", "width=450,height=320,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
}