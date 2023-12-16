//根据传递过来的参数name获取对应的值
function getParameter(name) {
    //(^|&): 这部分匹配参数名的起始位置。它表示参数名要么出现在字符串的开头，要么紧跟在 & 符号之后。
    // name: 这是一个变量，代表要匹配的参数名。
    // =: 这匹配参数名和参数值之间的等号。
    // ([^&]*): 这部分匹配参数值。它表示零个或多个非 & 字符的序列，这是因为参数值可以包含多个字符。
    // (&|$): 这部分匹配参数值的结束位置。它表示参数值要么紧跟在 & 符号之后，要么出现在字符串的末尾。
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);

    //location.search: location 对象提供了有关当前页面 URL 的信息，其中 location.search 包含查询字符串，通常以 ? 开头。
    //
    // substr(1): 这部分代码将从查询字符串中去掉开头的问号 ?，因此它返回的是不包含问号的查询字符串。
    //
    // .match(reg): 这是 JavaScript 字符串的 match 方法，它用给定的正则表达式 reg 在字符串中查找匹配项。如果找到匹配，match 方法返回一个数组，其中包含与正则表达式匹配的子字符串。如果没有找到匹配，它返回 null。
    if (r!=null) return (r[2]); return null;
}