/**
 * Created by ligson on 2014/12/5 0005.
 */

window.alert = function (msg) {
    $.messager.alert('提示信息', msg);
};
$(function () {
    $(window).load(function () {
        $(".ui-mask").hide();
    });
    //easyui combo高度样式修正
    $(".combo").click(function () {
        var comboPanel = $(".combo-panel");
        for (var i = 0; i < comboPanel.length; i++) {
            var cp = $(comboPanel[i]);
            var items = cp.find(".combobox-item");
            if (items.length > 0) {
                var h = 25 * items.length;
                //cp.css("height",h);
                cp.height(h);
            }
        }
    });

    //自定义验证器
    $.extend($.fn.validatebox.defaults.rules, {
        number: {
            validator: function (value, param) {
                return /^[0-9]+(.[0-9]{1,3})?$/.test(value);
            },
            message: '请输入数字类型'
        },
        timelen: {
            validator: function (value, param) {
                return /^\d{2}:\d{2}:\d{2}$/.test(value);
            },
            message: '请输入HH:mm:ss格式'
        }
    });


    //currentTopMenu
    var pathName = window.location.pathname;

    var menuItems = $("#topmenu").find("a");
    $.each(menuItems, function (idx, menu) {
        var href = menu.href;
        var arrays = href.split("/");
        if (arrays.length > 4) {
            var pathName2 = arrays[3];
            if (pathName.indexOf(pathName2) >= 0) {
                $(menu).addClass("currentTopMenu");
            }
        }
    });

});

var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var baseUrl = '';
if ('manager' != contextPath) {
    baseUrl = localObj.protocol + "//" + localObj.host + "/";
} else {
    baseUrl = localObj.protocol + "//" + localObj.host + "/" + contextPath + "/";
}

$(function () {
    var westPanel = $("#westPanel");
    var about = westPanel.attr("about");
    var liMenu = westPanel.find("li");
    liMenu.click(function () {
        var index = liMenu.index($(this));
        $.cookie("admin-menu-index-" + about, index);
    });

    var showIndex = $.cookie("admin-menu-index-" + about);
    var accordion = $(".easyui-accordion");
    if (showIndex) {
        var navTree = $(liMenu[showIndex]).parents(".easyui-tree");
        var panel = $(liMenu[showIndex]).parents(".panel")[0];
        var panelArray = accordion.find(".panel");
        var panelIndex = panelArray.index(panel);
        accordion.accordion("select", panelIndex);
        var navTitle = $(panel).find(".panel-title").html() + "&gt;&gt;" + $(liMenu[showIndex]).find("a").html();
        $("#mainDiv").panel("setTitle", navTitle)
    }

    $(".ism_nav_item").click(function () {
        for (var obj in $.cookie()) {
            if (typeof(obj) == "string") {
                var proName = obj + "";
                if (proName.indexOf("admin-menu-index-") != -1) {
                    $.cookie(proName, null);
                }
            }
        }

    });


    //自定义验证器
    $.extend($.fn.validatebox.defaults.rules, {
        number: {
            validator: function (value, param) {
                return /^[0-9]+(.[0-9]{1,3})?$/.test(value);
            },
            message: '请输入数字类型'
        }
    });
});