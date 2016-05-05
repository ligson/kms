/**
 * Created by Flying on 2015/4/19.
 */


/**
 * 我提问的问题
 */
/*var questionNum = $('#main_contents').children('.cs-item').length;*/
function loadContent() {
    var questionNum = $('#main_contents').children('.cs-item').length;
    $.ajax({
        type: 'POST',
        url: baseUrl + "user/loadMyAttentionQuestion",
        data: {offset: questionNum, format: "json"},
        dataType: 'json',
        success: function (data) {
            loadAjaxData(data);
        }

    });
}
$(function () {
    loadContent();
    $("#bp_more_attention").click(function () {
        loadContent();
    });
});


/**
 * 加载问题列表
 * @param question
 */
function loadQuestion(question) {
    var htmlString = "<div class=\"cs-item\">";
    htmlString += "<div class=\"mod-head \" >";
    htmlString += "<p class= \"text-color-999 \">";
    htmlString += "<a title=\"" + question.creator.nickName + "\" class=\"img aw-border-radius-5\" href=\"/user/view?id=" + question.creator.id + "\" data-id=\"5\"><img style='width:50px;height:50px;' onerror=\"this.src='/images/pic_user.gif'\" src=\"" + question.creator.photo + "\"></a>";
    htmlString += "<span>";
    htmlString += "</span>";
    htmlString += "<a href=\"  " + baseUrl + "question/view?id=" + question.id + "\" class=\" cs-topic-name\">";
    htmlString += question.title;
    htmlString += "</a>";
    htmlString += "<a href='" + baseUrl + "user/deleteQuestionAttention?id=" + question.id + "' class='pull-right'>取消关注</a>";
    htmlString += "</p>";
    htmlString += "<h4>";
    htmlString += "<span>";
    htmlString += "</span>";
    htmlString += "</h4>";
    htmlString += "</div>";
    htmlString += "</div>";

    $("#main_contents").append(htmlString);
}

/**
 * 加载Ajax请求资源
 * @param data
 */
function loadAjaxData(data) {
    if (data.success) {
        $.each(data.questionList, function (index, question) {
            loadQuestion(question)
        });
    } else {
        $("#btn_msg").html("没有更多问题了")
    }
}



