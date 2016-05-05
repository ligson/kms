/**
 * Created by ligson on 2015/7/10.
 */
$(function () {
    $("#applyWithDrawForm").bootstrapValidator({
        message: '输入格式不正确！',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            money: {
                validators: {
                    notEmpty: {
                        message: '该项不能为空!'
                    },
                    numeric: {
                        message: '请输入数字格式'
                    },
                    greaterThan: {
                        value: 0,
                        message: "悬赏金额请大于0"
                    },
                    lessThan: {
                        value: maxMoney,
                        message: "悬赏数字不能超过账户余额" + maxMoney
                    }
                }
            },
            payAccount: {
                validators: {
                    notEmpty: {
                        message: '支付账户禁止为空！'
                    }
                }
            },
            comments: {}

        }
    });
});

