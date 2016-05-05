/**
 * Created by ligson on 2015/11/5.
 * @author ligson
 * @description 身份证校验，依赖于string.js和date.js
 */
/**
 *         <p>
 *         类说明:身份证合法性校验
 *         </p>
 *         <p>
 *         --15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。
 *         --18位身份证号码：第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
 *         </p>
 */

/**
 * 省，直辖市代码表： { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
	 * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
	 * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
	 * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
	 * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
	 * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
 */

var IdcardValidator = function () {
    IdcardValidator.prototype.codeAndCity = [["11", "北京"], ["12", "天津"],
        ["13", "河北"], ["14", "山西"], ["15", "内蒙古"], ["21", "辽宁"],
        ["22", "吉林"], ["23", "黑龙江"], ["31", "上海"], ["32", "江苏"],
        ["33", "浙江"], ["34", "安徽"], ["35", "福建"], ["36", "江西"],
        ["37", "山东"], ["41", "河南"], ["42", "湖北"], ["43", "湖南"],
        ["44", "广东"], ["45", "广西"], ["46", "海南"], ["50", "重庆"],
        ["51", "四川"], ["52", "贵州"], ["53", "云南"], ["54", "西藏"],
        ["61", "陕西"], ["62", "甘肃"], ["63", "青海"], ["64", "宁夏"],
        ["65", "新疆"], ["71", "台湾"], ["81", "香港"], ["82", "澳门"],
        ["91", "国外"]];

    IdcardValidator.prototype.cityCode = ["11", "12", "13", "14", "15", "21", "22",
        "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
        "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
        "64", "65", "71", "81", "82", "91"];

// 每位加权因子
    IdcardValidator.prototype.power = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];

// 第18位校检码
    IdcardValidator.prototype.verifyCode = ["1", "0", "X", "9", "8", "7", "6", "5",
        "4", "3", "2"];


    /**
     * 验证所有的身份证的合法性
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.isValidatedAllIdcard = function (idcard) {
        if (idcard.length == 15) {
            idcard = this.convertIdcarBy15bit(idcard, "yyMMdd");
        }
        return this.isValidate18Idcard(idcard);
    };

    /**
     * <p>
     * 判断18位身份证的合法性
     * </p>
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * <p>
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     * </p>
     * <p>
     * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
     * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * </p>
     * <p>
     * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
     * 2 1 6 3 7 9 10 5 8 4 2
     * </p>
     * <p>
     * 2.将这17位数字和系数相乘的结果相加。
     * </p>
     * <p>
     * 3.用加出来和除以11，看余数是多少？
     * </p>
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 2。
     * <p>
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     * </p>
     *
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.isValidate18Idcard = function (idcard) {
        // 非18位为假
        if (idcard.length != 18) {
            return false;
        }
        // 获取前17位
        var idcard17 = idcard.substring(0, 17);
        // 获取第18位
        var idcard18Code = idcard.substring(17, 18);
        var c = null;
        var checkCode = "";
        // 是否都为数字
        if (this.isDigital(idcard17)) {
            c = idcard17.toString();
        } else {
            return false;
        }

        if (null != c) {
            var bit = [];
            bit = this.converCharToInt(c);

            var sum17 = 0;

            sum17 = this.getPowerSum(bit);

            // 将和值与11取模得到余数进行校验码判断
            checkCode = this.getCheckCodeBySum(sum17);
            if (null == checkCode) {
                return false;
            }
            // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                return false;
            }
        }
        return true;
    };

    /**
     * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供。
     *
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.isValidate15Idcard = function (idcard, fmt) {
        // 非15位为假
        if (idcard.length != 15) {
            return false;
        }

        // 是否全都为数字
        if (this.isDigital(idcard)) {
            var provinceid = idcard.substring(0, 2);
            var birthday = idcard.substring(6, 12);
            var year = parseInt(idcard.substring(6, 8));
            var month = parseInt(idcard.substring(8, 10));
            var day = parseInt(idcard.substring(10, 12));

            // 判断是否为合法的省份
            var flag = false;
            for (var i = 0; i < this.cityCode.length; i++) {
                var id = this.cityCode[i];
                if (id == (provinceid)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
            // 该身份证生出日期在当前日期之后时为假
            var birthdate = null;
            try {
                birthdate = Date.parseFromText(birthday, fmt);
            } catch (e) {
            }
            if (birthdate == null || new Date().before(birthdate)) {
                return false;
            }

            // 判断是否为合法的年份
            var curDay = new Date();
            var curYear = curDay.getFullYear();
            var year2bit = parseInt(curYear.toString().substring(2));

            // 判断该年份的两位表示法，小于50的和大于当前年份的，为假
            if ((year < 50 && year > year2bit)) {
                return false;
            }

            // 判断是否为合法的月份
            if (month < 1 || month > 12) {
                return false;
            }

            // 判断是否为合法的日期
            var mflag = false;
            curDay.setTime(birthdate);  //将该身份证的出生日期赋于对象curDay
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    mflag = (day >= 1 && day <= 31);
                    break;
                case 2: //公历的2月非闰年有28天,闰年的2月是29天。
                    if (Date.isLeapYear(curDay.getFullYear())) {
                        mflag = (day >= 1 && day <= 29);
                    } else {
                        mflag = (day >= 1 && day <= 28);
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    mflag = (day >= 1 && day <= 30);
                    break;
            }
            if (!mflag) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    };

    /**
     * 将15位的身份证转成18位身份证
     *
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.convertIdcarBy15bit = function (idcard, fmt) {
        var idcard17 = null;
        // 非15位身份证
        if (idcard.length != 15) {
            return null;
        }

        if (this.isDigital(idcard)) {
            // 获取出生年月日
            var birthday = idcard.substring(6, 12);
            var birthdate = null;
            try {
                birthdate = Date.parseFromText(birthday, fmt);

                var cday = new Date();
                cday.setTime(birthdate);
                var year = String.valueOf(cday.getFullYear());

                idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

                var c = idcard17;
                var checkCode = "";

                if (null != c) {
                    var bit = [];

                    // 将字符数组转为整型数组
                    bit = this.converCharToInt(c);
                    var sum17 = 0;
                    sum17 = this.getPowerSum(bit);

                    // 获取和值与11取模得到余数进行校验码
                    checkCode = this.getCheckCodeBySum(sum17);
                    // 获取不到校验位
                    if (null == checkCode) {
                        return null;
                    }

                    // 将前17位与第18位校验码拼接
                    idcard17 += checkCode;
                }
            } catch (e) {
            }
        } else { // 身份证包含数字
            return null;
        }

        return idcard17;
    };

    /**
     * 15位和18位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.isIdcard = function (idcard) {
        return idcard == null || ("" == idcard) ? false : /(^\d{15}$)|(\d{17}(?:\d|x|X)$)/.test(idcard);
    };

    /**
     * 15位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.is15Idcard = function (idcard) {
        return idcard == null || ("" == idcard) ? false : /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(idcard);
    };

    /**
     * 18位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    IdcardValidator.prototype.is18Idcard = function (idcard) {
        return /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([\d|x|X]{1})$/.test(idcard);
    };

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    IdcardValidator.prototype.isDigital = function (str) {
        return str == null || ("" == str) ? false : str.match("^[0-9]*$");
    };

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit
     * @return
     */
    IdcardValidator.prototype.getPowerSum = function (bit) {

        var sum = 0;

        if (this.power.length != bit.length) {
            return sum;
        }

        for (var i = 0; i < bit.length; i++) {
            for (var j = 0; j < this.power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * this.power[j];
                }
            }
        }
        return sum;
    };

    /**
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param checkCode
     * @param sum17
     * @return 校验位
     */
    IdcardValidator.prototype.getCheckCodeBySum = function (sum17) {
        var checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
        }
        return checkCode;
    };

    /**
     * 将字符数组转为整型数组
     *
     * @param c
     * @return
     * @throws NumberFormatException
     */
    IdcardValidator.prototype.converCharToInt = function (c) {
        var a = [];
        for (var i = 0; i < c.length; i++) {
            var temp = c[i];
            a[i] = parseInt(temp.toString());
        }
        return a;
    };

    /**
     * 验证身份证号合法性
     * @param idNumber
     * @return
     */
    IdcardValidator.prototype.isValidIdNumber = function (idNumber) {
        var flag = false;
        if (idNumber.length == 15) {
            flag = this.isValidate15Idcard(idNumber, "yyMMdd");
        } else if (idNumber.length == 18) {
            flag = this.isValidate18Idcard(idNumber);
        }
        return flag;
    };
};
