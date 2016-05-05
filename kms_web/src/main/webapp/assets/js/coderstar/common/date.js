/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * eg:
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.format = function (fmt) {
    //alert(parttern);
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()
        //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};
Date.parseFromText = function (text, parttern) {
    parttern = parttern.toString();
    text = text.toString();
    var yearOffset = parttern.indexOf("yyyy");
    var year = null;
    if (yearOffset != -1) {
        var yearString = text.substring(yearOffset, yearOffset + 4);
        if (!yearString.isInt()) {
            throw "年份无效";
        }
        year = parseInt(yearString);
    }
    var monthOffset = parttern.indexOf("MM");
    var month = null;
    if (monthOffset != -1) {
        var monthString = text.substring(monthOffset, monthOffset + 2);
        if (!monthString.isInt()) {
            throw "月份无效";
        }
        month = parseInt(monthString)-1;
        if (month < 1 || month > 12) {
            throw "月份无效";
        }
    }
    var dayOffset = parttern.indexOf("dd");
    var day = null;
    if (dayOffset != -1) {
        var dayString = text.substring(dayOffset, dayOffset + 2);
        if (!dayString.isInt()) {
            throw  "日期无效";
        }
        day = parseInt(dayString);
        if (day < 1 || day > 31) {
            throw  "日期无效";
        }
    }
    var hourOffset = parttern.indexOf("HH");
    var hour = null;
    if (hourOffset != -1) {
        var hourString = text.substring(hourOffset, hourOffset + 2);
        if (!hourString.isInt()) {
            throw  "小时无效";
        }
        hour = parseInt(hourString);
        if (hour < 0 || hour > 59) {
            throw  "小时无效";
        }
    }
    var minuteOffset = parttern.indexOf("mm");
    var minute = null;
    if (minute != -1) {
        var minuteString = text.substring(minuteOffset, minuteOffset + 2);
        if (!minuteString.isInt()) {
            throw  "分钟无效";
        }
        minute = parseInt(minuteString);
        if (minute < 0 || minute > 59) {
            throw  "分钟无效";
        }
    }

    var secondOffset = parttern.indexOf("ss");
    var second = null;
    if (secondOffset != -1) {
        var secondString = text.substring(secondOffset, secondOffset + 2);
        if (!secondString.isInt()) {
            throw "秒数无效";
        }
        second = parseInt(secondString);
        if (second < 0 || second > 59) {
            throw  "秒数无效";
        }
    }

    var msecondOffset = parttern.indexOf("S");
    var msecond = null;
    if (msecondOffset != -1) {
        var msecondString = text.substring(msecondOffset, msecondOffset + 1);
        if (!msecondString.isInt()) {
            throw "毫秒无效";
        }
        msecond = parseInt(msecondString);
        if (msecond < 0 || msecond > 999) {
            throw "毫秒无效";
        }
    }
    var date = new Date();
    if (year) {
        date.setYear(year);
    }
    if (month) {
        date.setMonth(month);
    }
    if (day) {
        date.setDate(day);
    }
    if (hour) {
        date.setHours(hour);
    }
    if (minute) {
        date.setMinutes(minute);
    }
    if (second) {
        date.setSeconds(second);
    }
    if (msecond) {
        date.setMilliseconds(msecond);
    }

    if (date.format(parttern) == text) {
        return date;
    } else {
        throw  "日期无效";
    }
};

/***
 *将yyyyMMddHHmmss格式转换为yyyy年MM月dd日 HH:mm:ss
 * @param text
 */
Date.convertTxtFormat = function (text) {
    return Date.parseFromText(text, "yyyyMMddHHmmss").format("yyyy年MM月dd日 HH:mm:ss");
};

Date.prototype.before = function (when) {
    return this.getMilliseconds() < when.getMilliseconds();
};
Date.prototype.after = function (when) {
    return this.getMilliseconds() > when.getMilliseconds();
};
/***
 * 是否闰年
 * @param intYear
 * @returns {boolean}
 */
Date.isLeapYear = function (intYear) {
    if (intYear % 100 == 0) {
        if (intYear % 400 == 0) {
            return true;
        }
    } else {
        if ((intYear % 4) == 0) {
            return true;
        }
    }
    return false;
};