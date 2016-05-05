/**
 * Created by ligson on 2015/5/20.
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
});

function supportRemark(remarkId, upOrDown) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
        return false;
    }
    $.ajax({
        url: '/article/supportRemark',
        data: {'remarkId': remarkId, isSupport: upOrDown == "up"},
        dateType: 'json',
        type: 'post',
        success: function (result) {
            // id="remark_${remark.id}_up_count"
            if (result.success) {
                var item = $("#remark_" + remarkId + "_" + upOrDown + "_count");
                var c = item.html();
                item.html(parseInt(c) + 1);
            } else {
                BootstrapDialog.alert({title: "提示信息", message: result.msg});
            }
        }, error: function (data) {
            BootstrapDialog.alert({title: "提示信息", message: "服务器也有生病的时候，您懂得！"});
        }
    });
}

function supportArticle(articleId, isSupport, target) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
        return false;
    }
    var supportBtn = $(target);
    $.ajax({
        url: '/article/supportArticle',
        data: {'articleId': articleId, isSupport: isSupport},
        dateType: 'json',
        type: 'post',
        success: function (result) {
            // id="remark_${remark.id}_up_count"
            if (result.success) {
                supportBtn.attr("disabled", "disabled");
            } else {
                BootstrapDialog.alert({title: "提示信息", message: result.msg});
            }
        }, error: function (data) {
            BootstrapDialog.alert({title: "提示信息", message: "服务器也有生病的时候，您懂得！"});
        }
    })
}
function rewardArticle(articleId, target) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
        return;
    }
    var html = "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo1\" value=\"1\"> 1码币</label>" +
        "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo2\" value=\"2\"> 2码币</label>" +
        "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo3\" value=\"5\"> 5码币</label>" +
        "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo4\" value=\"10\"> 10码币</label>" +
        "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo5\" value=\"20\"> 20码币</label>" +
        "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo6\" value=\"50\"> 50码币</label>" +
        "<label class=\"radio-inline\"><input type=\"radio\" name=\"moneyRdo\" id=\"moneyRdo7\" value=\"100\"> 100码币</label>";
    BootstrapDialog.show({
        title: '选择金额:',
        message: html,
        buttons: [{
            id: 'btn-ok',
            icon: 'glyphicon glyphicon-check',
            label: '打赏',
            cssClass: 'btn-primary',
            autospin: false,
            action: function (dialogRef) {
                var money = $("input[name=moneyRdo]:checked").val();
                if (money) {
                    $.post("/article/rewardArticle", {id: articleId, money: money}, function (data) {
                        if (!data.success) {
                            BootstrapDialog.alert({title: "提示信息", message: data.msg});
                        } else {
                            dialogRef.close();
                        }
                    });
                }
                //dialogRef.close();
            }
        }]
    });
    /*var rewardBtn = $(target);
     $.ajax({
     url: '/article/rewardArticle',
     data: {'articleId': articleId},
     dateType: 'json',
     type: 'post',
     success: function (result) {
     if (result.success) {

     } else {
     BootstrapDialog.alert({title: "提示信息", message: result.msg});
     }
     }, error: function (data) {
     BootstrapDialog.alert({title: "提示信息", message: "服务器也有生病的时候，您懂得！"});
     }
     })*/
}

function attentionArticle(articleId, obj) {
    if (!pageConfig.isLogin) {
        BootstrapDialog.alert({title: '提示信息', message: '您没有登录吧?请登录！'});
        return;
    }
    var flag = $(obj).attr("data-flag");
    //flag = true 取消关注，flag=false 增加关注
    $.post("/article/attentionArticle", {articleId: articleId, flag: flag}, function (data) {
        if (data.success) {
            BootstrapDialog.alert({title: "提示信息", message: data.msg});
            //切换页面展示
            var desc = "";
            if (flag == "0") {
                desc = "关注本文";
                $(obj).attr("data-flag", 1);
            } else {
                desc = "取消关注";
                $(obj).attr("data-flag", 0);
            }
            $("#attentionArticleBtn").html(desc);
        } else {
            BootstrapDialog.alert({title: "提示信息", message: data.msg});
        }
    });
}