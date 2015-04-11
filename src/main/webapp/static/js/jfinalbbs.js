/**
 * Created by liuyang on 15/4/2.
 */
var tab = {
    "ask": "讨论",
    "blog": "博客",
    "code": "代码"
};

function formatDateTime(datetime) {
    datetime = datetime.replace("-", "/")
    var current_date = new Date().getTime();
    var date = new Date(datetime).getTime();
    var mul = current_date - date;
    var time = parseInt(mul / 1000);
    if (time < 60) {
        return "刚刚";
    } else if (time < 3600) {
        return parseInt(time / 60) + " 分钟前";
    } else if (time < 86400) {
        return parseInt(time / 3600) + " 小时前";
    } else if (time < 604800) {
        return parseInt(time / 86400) + " 天前";
    } else if (time < 2419200) {
        return parseInt(time / 604800) + " 周前";
    } else if (time < 31536000) {
        return parseInt(time / 2592000) + " 个月前";
    } else {
        return parseInt(time / 31536000) + " 年前";
    }
    return datetime;
}
