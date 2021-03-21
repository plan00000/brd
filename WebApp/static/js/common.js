String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "")
};
String.prototype.ltrim = function() {
    return this.replace(/(^\s*)/g, "")
};
String.prototype.rtrim = function() {
    return this.replace(/(\s*$)/g, "")
};
function CommValidate() {}
CommValidate.isNumber = function(a) {
    return a.match(/\D/) == null
};
CommValidate.isZeroDouble=function(a){
	if(a.toString().match(/^[0][.](\d+)$/)==null){
		return false;
	}
	return true;
};
CommValidate.isDouble = function(a) {
    if (a.toString().match(/^[-+]?\d+(\.\d+){1}$/g) == null) {
        return false
    }
    return true
};
CommValidate.isPositiveDouble = function(a) {
    if (a.match(/^[+]?\d+(\.\d+){1}$/g) == null) {
        return false
    }
    return true
};
CommValidate.isInteger = function(a) {
    if (a.toString().match(/^[-+]?\d+$/) == null) {
        return false
    }
    return true
};
CommValidate.isPositiveInteger = function(a) {
    if (a.match(/^[+]?\d*$/) == null) {
        return false
    }
    return true
};
CommValidate.isRealname = function(b) {
    var a = /[^\u4E00-\u9FA5a-zA-Z]/;
    return a.test(b)
};
CommValidate.isUsername = function(b) {
    if (b.match(/\D/) == null) {
        return false
    }
    var a = /^\w+$/;
    return a.test(b)
};
CommValidate.isPassword = function(a) {
    var b = /^[\w~!@#$%^&*()_+-=\[\]{};'\:"|,.`\/<>?]+$/;
    return b.test(a)
};
CommValidate.isNullStr = function(a) {
    return a.trim().length == 0 ? true: false
};
CommValidate.isStr = function(a) {
    return /[^\x00-\xff]/g.test(a) ? false: true
};
CommValidate.contrainChinese = function(a) {
    if (escape(a).indexOf("%u") != -1) {
        return true
    } else {
        return false
    }
};
CommValidate.checkPassword = function(a) {
    if (escape(a).indexOf("%u") > -1) {
        return false
    }
    if (a.indexOf(" ") > -1) {
        return false
    }
};
CommValidate.checkMoney = function(b) {
    var a = /^\d{1,12}(?:\.\d{1,4})?$/;
    return a.test(b)
};
CommValidate.checkEmail = function(a) {
    var b = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    return b.test(a)
};
CommValidate.checkTel = function(a) {
    var b = /^([0-9]|[-])+$/g;
    if (a.length < 7 || a.length > 18) {
        return false
    } else {
        return b.exec(a)
    }
};
CommValidate.checkPhone = function(a) {
    var b = /^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
    return b.test(a)
};
CommValidate.checkCard = function(c) {
    var b = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
    var a = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Za-z])$/;
    if (c.match(b) == null && c.match(a) == null) {
        return false
    } else {
        return true
    }
};
CommValidate.checkIPAddress = function(b) {
    var a = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    if (b.match(a) == null) {
        return false
    } else {
        return true
    }
};
CommValidate.checkURL = function(a) {
    if (a.match(/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i) == null) {
        return false
    } else {
        return true
    }
};
CommValidate.checkQuote = function(c) {
    var a = new Array("~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "{", "}", "[", "]", "(", ")");
    a.push(":", ";", "'", "|", "\\", "<", ">", "?", "/", "<<", ">>", "||", "//");
    a.push("admin", "administrators", "administrator", "管理员", "系统管理员");
    a.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
    a.push("·");
    c = c.toLowerCase();
    for (var b = 0; b < a.length; b++) {
        if (c.indexOf(a[b]) >= 0) {
            return true
        }
    }
    return false
};
CommValidate.checkQuote2 = function(c) {
    var a = new Array("~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "{", "}", "[", "]", "（", "）");
    a.push(":", ";", "'", "|", "\\", "<", ">", "?", "/", "<<", ">>", "||", "//");
    a.push("admin", "administrators", "administrator", "管理员", "系统管理员");
    a.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
    a.push("·");
    c = c.toLowerCase();
    for (var b = 0; b < a.length; b++) {
        if (c.indexOf(a[b]) >= 0) {
            return true
        }
    }
    if (c.indexOf("(") > 0 && c.indexOf(")") > c.indexOf("(") + 1) {
        return false
    }
    if (c.indexOf("(") >= 0) {
        return true
    }
    if (c.indexOf(")") >= 0) {
        return true
    }
    return false
};
CommValidate.Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
CommValidate.ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];
CommValidate.IdCardValidate = function(a) {
    if (a.length == 15) {
        return CommValidate._isValidityBrithBy15IdCard(a)
    } else {
        if (a.length == 18) {
            var b = a.split("");
            if (CommValidate._isValidityBrithBy18IdCard(a) && CommValidate._isTrueValidateCodeBy18IdCard(b)) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
};
CommValidate._isValidityBrithBy15IdCard = function(d) {
    var c = d.substring(6, 8);
    var e = d.substring(8, 10);
    var a = d.substring(10, 12);
    var b = new Date(c, parseFloat(e) - 1, parseFloat(a));
    if (b.getYear() != parseFloat(c) || b.getMonth() != parseFloat(e) - 1 || b.getDate() != parseFloat(a)) {
        return false
    } else {
        return true
    }
};
CommValidate._isValidityBrithBy18IdCard = function(b) {
    var d = b.substring(6, 10);
    var e = b.substring(10, 12);
    var a = b.substring(12, 14);
    var c = new Date(d, parseFloat(e) - 1, parseFloat(a));
    if (c.getFullYear() != parseFloat(d) || c.getMonth() != parseFloat(e) - 1 || c.getDate() != parseFloat(a)) {
        return false
    } else {
        return true
    }
};
CommValidate._isTrueValidateCodeBy18IdCard = function(a) {
    var c = 0;
    if (a[17].toLowerCase() == "x") {
        a[17] = 10
    }
    for (var b = 0; b < 17; b++) {
        c += CommValidate.Wi[b] * a[b]
    }
    valCodePosition = c % 11;
    if (a[17] == CommValidate.ValideCode[valCodePosition]) {
        return true
    } else {
        return false
    }
};
CommValidate.checkUnit = function(b, a) {
    switch (b) {
    case "株":
    case "丛":
    case "棵":
        if (CommValidate.isPositiveInteger(a.toString())) {
            return 0
        }
        return 1;
    case "平方米":
    case "亩":
        if (CommValidate.isPositiveDouble(a.toString()) || CommValidate.isPositiveInteger(a.toString())) {
            return 0
        }
        return 2
    }
};
CommValidate.checkNum = function(b, a) {
    if (a.trim().length == 0) {
        showDialogWithOk("请输入数量");
        return - 1
    }
    if (!CommValidate.isDouble(a) && !CommValidate.isInteger(a)) {
        showDialogWithOk("数量格式错误");
        return - 1
    }
    if (parseFloat(a) <= 0) {
        if (parseFloat(a) == 0) {
            showDialogWithOk("数量不能是0")
        } else {
            showDialogWithOk("数量不能是负数")
        }
        return - 1
    }
    a = CommTool.toFixed(a);
    if (CommValidate.checkUnit(b, parseFloat(a)) == 0) {
        return a
    }
    showDialogWithOk("数量和单位不符合");
    return - 1
};
function CommTool() {}
CommTool.getBrowserName = function() {
    var a = {};
    var b = navigator.userAgent.toLowerCase();
    var c; (c = b.match(/rv:([\d.]+)\) like gecko/)) ? a.ie = c[1] : (c = b.match(/msie ([\d.]+)/)) ? a.ie = c[1] : (c = b.match(/firefox\/([\d.]+)/)) ? a.firefox = c[1] : (c = b.match(/chrome\/([\d.]+)/)) ? a.chrome = c[1] : (c = b.match(/opera.([\d.]+)/)) ? a.opera = c[1] : (c = b.match(/version\/([\d.]+).*safari/)) ? a.safari = c[1] : 0;
    if (a.ie) {
        return "ie"
    }
    if (a.firefox) {
        return "firefox"
    }
    if (a.chrome) {
        return "chrome"
    }
    if (a.opera) {
        return "opera"
    }
    if (a.safari) {
        return "safari"
    }
};
CommTool.getBrowserVersion = function() {
    var a = {};
    var b = navigator.userAgent.toLowerCase();
    var c; (c = b.match(/rv:([\d.]+)\) like gecko/)) ? a.ie = c[1] : (c = b.match(/msie ([\d.]+)/)) ? a.ie = c[1] : (c = b.match(/firefox\/([\d.]+)/)) ? a.firefox = c[1] : (c = b.match(/chrome\/([\d.]+)/)) ? a.chrome = c[1] : (c = b.match(/opera.([\d.]+)/)) ? a.opera = c[1] : (c = b.match(/version\/([\d.]+).*safari/)) ? a.safari = c[1] : 0;
    if (a.ie) {
        return a.ie
    }
    if (a.firefox) {
        return a.firefox
    }
    if (a.chrome) {
        return a.chrome
    }
    if (a.opera) {
        return sys.opera
    }
    if (a.safari) {
        return a.safari
    }
};
CommTool.toFixed = function(d) {
    d = d.toString();
    var c = parseFloat(d);
    var b = c.toString().indexOf(".");
    if (b != -1) {
        var a = d.substring(b + 1, c.toString().length);
        if (a.length > 2) {
            return c.toFixed(2)
        }
    }
    return c
};
CommTool.hideMobile = function(b) {
    var a = b.length;
    if ((a - 8) <= 0) {
        return b
    }
    var c = b.substr(0, 4) + "****" + b.substr(8, a - 8);
    return c
};
CommTool.hideCardId = function(b) {
    var a = b.length;
    if (a == 15) {
        var c = b.substr(0, 8) + "****" + b.substr(12, a - 12);
        return c
    } else {
        var c = b.substr(0, 6) + "*********" + b.substr(15, a - 15);
        return c
    }
};
CommTool.fbBackspace = function(h) {
    var d = h || window.event;
    var g = d.target || d.srcElement;
    var c = g.type || g.getAttribute("type");
    var f = g.getAttribute("readonly");
    var i = g.getAttribute("enabled");
    f = (f == null) ? false: f;
    i = (i == null) ? true: i;
    var b = (d.keyCode == 8 && (c == "password" || c == "text" || c == "textarea") && (f == true || i != true)) ? true: false;
    var a = (d.keyCode == 8 && c != "password" && c != "text" && c != "textarea") ? true: false;
    if (a) {
        return false
    }
    if (b) {
        return false
    }
};
Date.prototype.Format = function(a) {
    var c = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    if (/(y+)/.test(a)) {
        a = a.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length))
    }
    for (var b in c) {
        if (new RegExp("(" + b + ")").test(a)) {
            a = a.replace(RegExp.$1, (RegExp.$1.length == 1) ? (c[b]) : (("00" + c[b]).substr(("" + c[b]).length)))
        }
    }
    return a
};
CommTool.beforeNow = function(b) {
    var e = new Date().Format("yyyy-MM-dd");
    var d = b.Format("yyyy-MM-dd");
    var c = e.split("-");
    var a = d.split("-");
    return [Date.parse(new Date(c[0], c[1], c[2])) <= Date.parse(new Date(a[0], a[1], a[2]))]
};
CommTool.bfNow = function(b) {
    var e = new Date().Format("yyyy-MM-dd");
    var d = b.Format("yyyy-MM-dd");
    var c = e.split("-");
    var a = d.split("-");
    return [Date.parse(new Date(c[0], c[1], c[2])) >= Date.parse(new Date(a[0], a[1], a[2]))]
};
CommTool.convertUrl = function(a) {
    if (/(http:\/\/)?hmeg.cn(:)?(\d)*\//.test(a)) {
        a = a.replace(/(http:\/\/)?hmeg.cn(:)?(\d)*\//, "");
        return a.replace(/;JSESSIONID=[A-Za-z0-9-]{36}/, "")
    }
    return a.replace(/;JSESSIONID=[A-Za-z0-9-]{36}/, "")
};
function getFaxPriceForSeller(a, c) {
    if (!CommValidate.isNullStr(a)) {
        var b = 0.01 * a * c;
        var d = Number((parseFloat(a) + parseFloat(b)).toFixed(2));
        return d
    } else {
        return a
    }
}
function getDateDiff(a, e) {
    var c = a.getTime();
    var b = e.getTime();
    var d = Math.abs((c - b)) / (1000 * 60 * 60 * 24);
    return d
}
function checkfield(c, b, a) {
    if (c.length == 0) {
        if (a.trim().length != 0) {
            return showMsg("请输入" + b)
        }
        return true
    }
    if (c.length > 9) {
        return showMsg("输入不能超过9个字符")
    }
    if (!CommValidate.isDouble(c) && !CommValidate.isInteger(c)) {
        return showMsg(b + "必须是数字")
    }
    if (parseFloat(c) > -0.000006 && parseFloat(c) < 0.000006) {
        c = 0
    }
    return true
}
function checknumval(b, a) {
    if (b.length == 0) {
        return 1
    }
    if (b.length > 9) {
        return 1
    }
    if (!CommValidate.isDouble(b) && !CommValidate.isInteger(b)) {
        return 2
    }
    if (CommValidate.isInteger(b)) {
        if (parseInt(b[0]) == 0) {
            return 3
        }
    }
    if (parseFloat(b) > -0.000006 && parseFloat(b) < 0.000006) {
        return 4
    }
    if (parseFloat(b) < 0) {
        return 5
    }
    if (CommValidate.isDouble(b) && a == "株") {
        return 6
    }
    return 0
}
CommValidate.delZero = function() {
    var a = this.value;
    a = (a + "").replace(/^0+\./g, "0.");
    this.value = a.match(/^0+[1-9]+/) ? a = a.replace(/^0+/g, "") : a
};
function adminleft(b) {
    $("#" + b).removeClass("hover").addClass("hover");
    var a = $("#" + b).parent().parent().parent().find("a.pta");
    a.parent().parent().addClass("open");
    a.parent().parent().find("ul.smenu").css("display", "block");
    lastItem = a
};