/**
 * Created by xiaoheidepro on 16/9/29.
 */
//计算天数差的函数，通用
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式
    var now = new Date(Date.parse(sDate1.replace(/\-/g, "/")));
    var date = new Date(Date.parse(sDate2.replace(/\-/g, "/")));
    return (now.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
}
//验证数值类型，可输入小数
function keyup_decimal(str){
    var value = $("#"+str).val();
    value = value.replace(/[^\-\d{1,}\.\d{1,}|\d{1,}]/g,'');
    //去掉多个.最多只能出现一次
    var num = occurNumber(value,'.');
    if(num > 1){//小数点多个
        value = value.substring(0,value.length-1);
    }else if(num == 1) {
        var dnum = value.indexOf(".");
        if(value.indexOf("-")==0){//负数开头
            if(dnum<2){
                value="-0.";
            }
        }else{
            if(dnum<1){
                value="0.";
            }
        }
    }
    if(value.indexOf("0")==0){
        if(value.length>=2){
            if(value.substring(1,2) != "." ){
                value = value.substring(1);
            }
        }
    }
    if(value.indexOf("-0")==0){
        if(value.length>=3){
            if(value.substring(2,3) != "." ){
                value = value.substring(0,1)+value.substring(2);
            }
        }
    }
    //－只能开头，中间不能有
    if(value.length>1){
        var staStr = value.substring(0,1);
        var endStr = value.substring(1);
        while (endStr.indexOf('-') >= 0){
            endStr = endStr.replace('-', '');
        }
        value = staStr + endStr
    }

    $("#"+str).val(value);
}
//验证数值类型，只能输入整数
function keyup_integer(str){
    var value = $("#"+str).val();
    value = value.replace(/[^\-\d{1,}\d{1,}|\d{1,}]/g,'');
    if(value.indexOf("0")==0){
        if(value.length>=2){
            value = value.substring(1);
        }
    }
    if(value.indexOf("-0")==0){
        if(value.length>=3){
            value = value.substring(0,1)+value.substring(2);
        }
    }

    //去掉中间的－号
    if(value.length>1){
        var staStr = value.substring(0,1);
        var endStr = value.substring(1);
        while (endStr.indexOf('-') >= 0){
            endStr = endStr.replace('-', '');
        }
        value = staStr + endStr
    }
    $("#"+str).val(value);
}
//失去焦点，小数的处理
function decimal_end(obj,type){
    var myvalue = obj.value;
    if(myvalue >= 0){//表示正数
        if(myvalue.indexOf("-")==0){
            myvalue = myvalue.substring(1);
        }
    }
    var num = occurNumber(myvalue,'.');
    if(num==0){
        if(myvalue.indexOf("-")==0){
            if(myvalue.length>=2){
                myvalue+=".00";
            }else{
                myvalue+="0.00";
            }
        }else{
            if(myvalue.length>=1){
                myvalue+=".00";
            }else{
                myvalue+="0.00";
            }
        }
    }
    obj.value = myvalue;
    if(type!="undefined" && type!="" && type >0 ){
        decimal_toThousands(obj);
    }
//        alert("聚焦");
//        decimal_star(obj);
}
//获取焦点，小数的处理
function decimal_star(obj){
    var myvalue = obj.value;
    //去掉千分符
    while (myvalue.indexOf(",") >= 0){
        myvalue = myvalue.replace(",", "");
    }
    if(myvalue == 0 ){
        obj.value = "";
    }else{
        if(myvalue.indexOf(".")>0){
            var starvalue = myvalue.substring(0,myvalue.indexOf("."));
            var endvalue = myvalue.substring(myvalue.indexOf(".")+1);
            if(endvalue<=0){
                obj.value = starvalue;
                return;
            }
        }
        obj.value = myvalue;
    }

}
//获取焦点，整数的处理
function integer_star(obj){
    var myvalue = obj.value;
    //去掉千分符
    while (myvalue.indexOf(",") >= 0){
        myvalue = myvalue.replace(",", "");
    }
    if(myvalue == 0){
        myvalue="";
    }
    while(myvalue.indexOf("0")==0){
        myvalue = myvalue.substring(1);
    }
    obj.value = myvalue;
}
//失去焦点，整数的处理
function integer_end(obj,type){
    var myvalue = obj.value;
    if(myvalue.indexOf("-")==0){
        if(myvalue == 0){
            obj.value = "0";
        }
    }
    if(myvalue==""||myvalue=="undefined"){
        obj.value = "0";
    }

    if(type!="undefined" && type!="" && type >0 ){
        integer_toThousands(obj);
    }
}
//获取字符在字符串出现次数
function occurNumber(str,re){
    var num=0;
    while (str.indexOf(re) >= 0){
        str = str.replace(re, '');
        num++;
    }
    return num;
}

//整数千分符
function integer_toThousands(obj) {
    obj.value = integer_thousands(obj.value);
}
//整数千分符
function integer_thousands(myvalue) {
    while (myvalue.indexOf(",") >= 0){
        myvalue = myvalue.replace(",", "");
    }
    var num = (myvalue || 0).toString(), result = '';
    while (num.length > 3) {
        result = ',' + num.slice(-3) + result;
        num = num.slice(0, num.length - 3);
    }
    if (num) { result = num + result; }
    return result;
}
//小数点后面内容添加千分符
function decimal_thousands(myvalue) {
    while (myvalue.indexOf(",") >= 0){
        myvalue = myvalue.replace(",", "");
    }
    var num = (myvalue || 0).toString(), result = '';
    while (num.length > 3) {
        result += num.substring(0,3) + ",";
//            result = ',' + num.slice(3) + result;
        num = num.substring(3);
    }
    if (num) { result = result + num ; }
    return result;
}
//小数千分符
function decimal_toThousands(obj) {
    var myvalue = obj.value;
    while (myvalue.indexOf(",") >= 0){
        myvalue = myvalue.replace(",", "");
    }
    if(myvalue.indexOf(".")>0){
        var starvalue = myvalue.substring(0,myvalue.indexOf("."));
        starvalue = integer_thousands(starvalue);
        var endvalue = myvalue.substring(myvalue.indexOf(".")+1);
        endvalue = decimal_thousands(endvalue);

        obj.value = starvalue+"."+endvalue;
    }else{
        obj.value =integer_thousands(obj.value);
    }
}
