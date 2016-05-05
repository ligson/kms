/**
 * Created by Administrator on 2015/4/16 0016.
 */

function addTag() {
    var tagTxt = $("#tagTxt");
    if ((tagTxt.val() + "").trim() != "") {
        if (tagTxt.val().length > 24) {
            BootstrapDialog.alert({title: "提示信息", message: "不能超过24个字！"});
        } else {
            var html = "<span title='双击我删除' ondblclick='$(this).remove()' class=\"cs-question-tags\" style=\"display:inline-block;\"><i class=\"icon icon-edit\"></i>" + tagTxt.val() + "</span>";
            $("#tagItems").append(html);
            tagTxt.val("");
            var tagValues = $(".cs-question-tags");
            var tagString = "";
            $.each(tagValues, function (idx, tag) {
                tagString += ";" + $(tag).text();
            });
            $("input[name='tags']").val(tagString);
        }
    }
}
$(function () {
    var tagTxt = $("#tagTxt");
    tagTxt.keyup(function (e) {
        var keycode = e.which;
        if (keycode == 32) {
            addTag();
        }
    });
    $("#addTagBtn").click(function () {
        addTag();

    });
    var createQuestionForm = $("#createQuestionForm");
    createQuestionForm.bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            title: {
                message: '问题标题格式不正确',
                validators: {
                    notEmpty: {
                        message: '问题标题禁止为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 128,
                        message: '标题长度应该在6到128之间'
                    }
                }
            },
            money: {
                validators: {
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
            languageId: {
                validators: {
                    notEmpty: {
                        message: '语言分类是必选的'
                    }

                }
            }
        }
    });

});