/**
 * Created by ligson on 2016/5/5.
 */
$(function () {
    if (typeof(Worker) == "undefined") {
        $(".login-container").hide();
        $(".ui-mask-box").show();
    }
});