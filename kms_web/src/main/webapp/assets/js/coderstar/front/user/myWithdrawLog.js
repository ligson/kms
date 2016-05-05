/**
 * Created by ligson on 2015/7/14.
 */
var offset = 0;

function loadMyRechangeLog() {
    $.post("/user/loadMyWithdrawLog", {offset: offset}, function (data) {
        if (data.success) {
            var html = "";

            for (var i = 0; i < data.withdrawList.length; i++) {
                html += "<tr>";
                var trade = data.withdrawList[i];
                html += "   <th scope=\"row\">" + trade.id + "</th>";
                html += "   <td>" + Date.convertTxtFormat(trade.createDate) + "</td>";
                html += "   <td>" + trade.money + "</td>";
                html += "   <td>" + trade.trueMoney + "</td>";
                var stateStr = "申请";
                if (trade.state == 1) {
                    stateStr = "申请";
                } else if (trade.state == 2) {
                    stateStr = "批准";
                } else {
                    stateStr = "拒绝";
                }
                html += "   <td>" + stateStr + "</td>";
                html += "   <td>" + trade.payAccount + "</td>";
                html += "   <td>" + trade.comments + "</td>";
                html += "</tr>";
            }

            offset += 10;
            $("#tradeLogList").append(html);
        }
    });
}
$(function () {
    loadMyRechangeLog();
    $("#bp_more").click(function () {
        loadMyRechangeLog();
    });
});
