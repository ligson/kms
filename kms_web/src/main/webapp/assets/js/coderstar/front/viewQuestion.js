/**
 * Created by ligson on 2015/4/18 0018.
 */

$(document).ready(function () {
    hljs.initHighlightingOnLoad();
    $('pre code').each(function (i, block) {
        hljs.highlightBlock(block);
    });
    $("#answer_form").submit(function () {
        if (!pageConfig.isLogin) {
            BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
            return false;
        }
        var value = CKEDITOR.instances.ckeditor01.getData();
        if ((value + "").trim().length == 0) {
            BootstrapDialog.alert({title: '提示信息', message: '回复不允许为空'});
            return false;
        }

    });

    var config = {
        extraPlugins: 'codesnippet',
        toolbar: [
            ['Bold', 'Italic', 'Underline', 'CodeSnippet']
        ],
        codeSnippet_theme: 'monokai_sublime'
    };

    var editor02 = CKEDITOR.replace('ckeditor02', config);

    $("#editDescBtn").click(function () {
        $(".cs-question-description-editor").toggleClass("hide");
    });

    $("#modifySbtBtn").click(function () {
        var qId = $("input[name='questionId']").val();
        var value = CKEDITOR.instances.ckeditor02.getData();
        $.post("/question/modifyDesc", {questionId: qId, description: value}, function (data) {
            if (data.success) {
                //$(".cs-question-description-editor").removeClass("hide");
                //BootstrapDialog.alert({title: '提示信息', message: "修改成功！"});
                window.location.reload()
            } else {
                BootstrapDialog.alert({title: '提示信息', message: data.msg});
            }
        });
    });

    $("input[name='auto_focus']").click(function () {
        var isCheck = $(this).prop("checked");
        var msg = isCheck ? "关注" : "取消关注";
        var qId = $("input[name='questionId']").val();
        $.post("/question/attentionQuestion", {questionId: qId}, function (data) {
            if (data.success) {
                BootstrapDialog.alert({title: '提示信息', message: msg + "成功！"});
            } else {
                BootstrapDialog.alert({title: '提示信息', message: data.msg});
            }
        });
    });
});

function attentionQuestion(questionId,obj) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: "提示信息", message: "您还没有登陆"});
        return;
    }
    var flag = $(obj).attr("data-flag");
    $.post("/question/attentionQuestion", {questionId: questionId,flag:flag}, function (data) {
        if (data.success) {
            BootstrapDialog.alert({title: "提示信息", message: data.msg});
            //切换页面展示
            var desc = "";
            if(flag == "0"){
                desc = "关注本文";
                $(obj).attr("data-flag",1);
            }else{
                desc = "取消关注";
                $(obj).attr("data-flag",0);
            }
            $("#attentionQuestionBtn").html(desc);
        } else {
            BootstrapDialog.alert({title: "提示信息", message:  data.msg});
        }
    });
}
function selectRightAsk(askId) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
        return false;
    }
    $.post("/question/selectRightAsk", {id: askId}, function (data) {
        if (data.success) {
            BootstrapDialog.alert({title: '提示信息', message: '选择成功!'});
            $("button[name=selectRightAskBtn]").remove();
        } else {
            BootstrapDialog.alert({title: '提示信息', message: data.msg});
        }

    });
}
function support(askId, upOrDown) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
        return false;
    }
    $.ajax({
        url: '/question/rateAsk',
        data: {'askId': askId, 'upOrDown': upOrDown},
        dateType: 'json',
        type: 'post',
        success: function (result) {
            if (result.success) {
                var item = $('#' + askId + upOrDown);
                var c = item.html();
                item.html(parseInt(c) + 1);
            } else {
                BootstrapDialog.alert({title: "提示信息", message: result.msg});
            }
        }, error: function (data) {
            BootstrapDialog.alert({title: "提示信息", message: "服务器也有生病的时候，您懂得！"});
        }
    })
}