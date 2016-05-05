/**
 * Created by ligson on 2015/7/13.
 */
var questionOffset = 0;
var articleOffset = 0;

function loadArticle() {
    $.post("/user/loadUserArticles", {id: userId, offset: articleOffset}, function (data) {
        if (data.success) {
            for (var i = 0; i < data.articleList.length; i++) {
                appendArticle(data.articleList[i]);
            }
            articleOffset += data.articleList.length;
        }
    });
}
function loadQuestion() {
    $.post("/user/loadUserQuestions", {id: userId, offset: questionOffset}, function (data) {
        if (data.success) {
            for (var i = 0; i < data.questionList.length; i++) {
                appendQuestion(data.questionList[i]);
            }
            questionOffset += data.questionList.length;
        }
    });
}

function appendArticle(article) {
    var htmlString = "<div class=\"cs-item\">";
    htmlString += "     <div class=\"cs-mod\">";
    htmlString += "         <div class=\"mod-head\">";
    htmlString += "             <h4 class=\"cs-hide-txt\">";
    htmlString += "                 <a href=\"/article/view?id=" + article.id + "\">" + article.title + "</a>";
    htmlString += "             </h4>";
    htmlString += "         </div>";
    htmlString += "         <div class=\"mod-body\">";
    htmlString += "             <span class=\"cs-border-radius-5 count pull-left\"><i class=\"glyphicon glyphicon-thumbs-up\"></i>" + article.replyNum + "</span>";
    htmlString += "             <p class=\"text-color-999\"></p>";
    htmlString += "             <p class=\"text-color-999\">" + Date.convertTxtFormat(article.createDate) + "</p>";
    htmlString += "         </div>";
    htmlString += "     </div>";
    htmlString += "</div>";
    $("#contents_user_actions_answers").append(htmlString);
}

function appendQuestion(question) {
    var htmlString = "<div class=\"cs-item\">";
    htmlString += "     <div class=\"cs-mod\">";
    htmlString += "         <div class=\"mod-head\">";
    htmlString += "             <h4 class=\"cs-hide-txt\"><a href=\"/question/view?id=" + question.id + "\">" + question.title + "</a></h4>";
    htmlString += "         </div>";
    htmlString += "         <div class=\"mod-body\">";
    htmlString += "             <span class=\"cs-border-radius-5 count pull-left\"><i class=\"glyphicon glyphicon-edit\"></i>" + question.replyNum + "</span>";
    htmlString += "             <p class=\"text-color-999\">" + question.viewNum + "次浏览 • " + question.attentionNum + "个关注</p>";
    htmlString += "             <p class=\"text-color-999\">" + Date.convertTxtFormat(question.createDate) + "</p>";
    htmlString += "         </div>";
    htmlString += "     </div>";
    htmlString += "</div>";
    $("#contents_user_actions_questions").append(htmlString);
}
$(function () {
    loadQuestion();
    loadArticle();
    $("#loadQuestionBtn").click(function () {
        loadQuestion();
    });
    $("#loadArticleBtn").click(function () {
        loadArticle();
    });
});