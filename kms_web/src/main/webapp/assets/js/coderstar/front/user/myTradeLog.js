/**
 * Created by ligson on 2015/7/14.
 */
var offset = 0;

function loadMyRechangeLog() {
    $.post("/user/loadMyTradeLog", {offset: offset}, function (data) {
        if (data.success) {
            var html = "";

            for (var i = 0; i < data.tradeRecords.length; i++) {
                html += "<tr>";
                var trade = data.tradeRecords[i];
                html += "   <th scope=\"row\">" + trade.id + "</th>";
                html += "   <td>" + Date.convertTxtFormat(trade.createDate) + "</td>";
                html += "   <td>" + (trade.type == 1 ? "支出" : "收入") + "</td>";
                html += "   <td>" + (trade.objType == 1 ? "问题" : "文章") + "</td>";
                html += "   <td>" + trade.objId + "</td>";
                html += "   <td>" + trade.money + "</td>";
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
