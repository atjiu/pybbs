var emailReg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
var topic_top = {1: "<span style='color: yellowgreen'>置顶</span>", 0: ""};
var topic_good = {1: "<span style='color: yellowgreen'>精华</span>", 0: ""};
function formatDateTime(datetime) {
    var current_date = new Date().getTime();
    var _date = datetime.split(" ")[0];
    var _time = datetime.split(" ")[1];
    var date = new Date();
    date.setFullYear(_date.split("-")[0]);
    date.setMonth(_date.split("-")[1] - 1);
    date.setDate(_date.split("-")[2]);
    date.setHours(_time.split(":")[0]);
    date.setMinutes(_time.split(":")[1]);
    date.setSeconds(_time.split(":")[2]);
    var mul = current_date - date.getTime();
    var time = parseInt(mul / 1000);
    if (time < 60) {
        return "刚刚"
    } else if (time < 3600) {
        return parseInt(time / 60) + " 分钟前"
    } else if (time < 86400) {
        return parseInt(time / 3600) + " 小时前"
    } else if (time < 604800) {
        return parseInt(time / 86400) + " 天前"
    } else if (time < 2592000) {
        return parseInt(time / 604800) + " 周前"
    } else if (time < 31536000) {
        return parseInt(time / 2592000) + " 个月前"
    } else {
        return parseInt(time / 31536000) + " 年前"
    }
    return datetime
}
