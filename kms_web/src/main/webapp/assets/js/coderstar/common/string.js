/***
 * String方法扩展
 * @author ligson
 */

String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
/***
 * 空串或者null或者trim后是空串
 * @returns {boolean}
 */
String.prototype.isEmpty = function () {
    if (this && (this + "").trim() != "") {
        return false;
    } else {
        return true;
    }
};

/***
 * 字符串测试
 * @param str
 * @returns {boolean}
 */
String.isEmpty = function (str) {
    if (str && (str + "").trim() != "") {
        return false;
    } else {
        return true;
    }
};

String.prototype.passwordLevel = function () {
    var reg1 = /^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
    if (reg1.test(this)) {
        return 2;
    }

    if (this.match(/\W/g) != null && this.match(/\d/g) != null && this.match(/[a-zA-Z]/g) != null) {
        return 3;
    }

    return 1;
};
/***
 * 验证邮箱格式
 * @returns {boolean}
 */
String.prototype.isEmail = function () {
    var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return pattern.test(this);
};
/***
 * 验证身份证格式
 * @returns {boolean}
 */
String.prototype.isIdCardNumber = function () {
    return new IdcardValidator().isValidatedAllIdcard(this);
};

/***
 * 验证电话或者传真
 * @returns {boolean}
 */
String.prototype.isPhone = function () {
    var pattern = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
    return pattern.test(this);
};

/***
 * 验证手机号,可以含-
 * @returns {boolean}
 */
String.prototype.isMobile = function () {
    var pattern = /^((\(\d{2,3}\))|(\d{3}\-))?1\d{10}$/;
    return pattern.test(this);
};
String.prototype.cut = function (maxLen) {
    if (this == null || this == undefined) {
        return "";
    }
    if (this.length > maxLen) {
        return this.substring(0, maxLen) + "...";
    } else {
        return this;
    }
};

//逆序
String.prototype.reverse = function () {
    return this.split("").reverse().join("");
};

//测试是否是数字
String.prototype.isNumeric = function () {
    var tmpFloat = parseFloat(this);
    if (isNaN(tmpFloat))
        return false;
    var tmpLen = this.length - tmpFloat.toString().length;
    return tmpFloat + "0".Repeat(tmpLen) == this;
};
//测试是否是整数
String.prototype.isInt = function () {
    if (this == "NaN")
        return false;
    var reg = /^-?\d+$/;
    return reg.test(this);
};
// 合并多个空白为一个空白
String.prototype.resetBlank = function () {
    return this.replace(/s+/g, " ");
};
// 除去左边空白
/**
 * @return {string}
 */
String.prototype.LTrim = function () {
    return this.replace(/^s+/g, "");
};
// 除去右边空白
/**
 * @return {string}
 */
String.prototype.RTrim = function () {
    return this.replace(/s+$/g, "");
};

// 保留数字
String.prototype.getNum = function () {
    return this.replace(/[^d]/g, "");
};
// 保留字母
String.prototype.getEn = function () {
    return this.replace(/[^A-Za-z]/g, "");
};
// 保留中文
String.prototype.getCn = function () {
    return this.replace(/[^u4e00-u9fa5uf900-ufa2d]/g, "");
};
// 得到字节长度
String.prototype.getRealLength = function () {
    return this.replace(/[^x00-xff]/g, "--").length;
};
// 从左截取指定长度的字串
String.prototype.left = function (n) {
    return this.slice(0, n);
};
// 从右截取指定长度的字串
String.prototype.right = function (n) {
    return this.slice(this.length - n);
};
// HTML编码
String.prototype.HTMLEncode = function () {
    var re = this;
    var q1 = [/x26/g, /x3C/g, /x3E/g, /x20/g];
    var q2 = ["&", "<", ">", " "];
    for (var i = 0; i < q1.length; i++)
        re = re.replace(q1[i], q2[i]);
    return re;
};
// Unicode转化
String.prototype.ascW = function () {
    var strText = "";
    for (var i = 0; i < this.length; i++)
        strText += "&#" + this.charCodeAt(i) + ";";
    return strText;
};

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};

String.prototype.equalsIgnoreCase = function (s1) {
    return this.toUpperCase() == s1.toUpperCase();
};
