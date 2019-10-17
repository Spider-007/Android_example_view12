/**
 * Created by xiaoheidepro on 16/10/29.
 */
var dataobj;


// 与移动端交互
function onDeviceReady() {
    getFormData("初始化");
    //通过获取表单数据的方法
    function getFormData(ocuID) {
        HTStartform.getData(
            ocuID,
            getDataSuccss
        );
    }
}
//请求成功
function getDataSuccss(str){
    initData(str);
}

/**
 * 页面初始化
 * @param mydata
 */
function initData(dataStr,seccessBak,failureBak) {

    try{
        dataobj=JSON.parse(dataStr);
        var myInit = new HT_EventInit(dataobj);
        mydomain();
        seccessBak();
    }catch(e){
        //alert(dataStr);
        //alert("JSON字符串有误"+e);
        failureBak();
        return ;
    }
}

/**
 * 字典类型，调用原生插件
 * @param myobj
 * @param ipputType
 */
function sendDic(myobj,ipputType) {
    HTStartform.getCheck(myobj.id,ipputType,getCheckSuccess);
}


/**
 * 成功回掉，字段初始化
 * @param myobj
 * @param ipputType
 */
function getCheckSuccess(jsonData){
    var myobj;
    try{
        myobj=JSON.parse(jsonData);
    }catch(e){
        alert("JSON字符串有误"+e);
    }

     assignValue(myobj.id,myobj.value);




}


///**
// * 成功回掉，字段初始化
// * @param myobj
// * @param ipputType
// */
//function getCheckSuccess(){
//
//    myCheckSuccess('{"id":"HZGDVIIDTbsRPoQUJA5Bo9e9f3Xfla8O_ENDDATE","name":"2016-11-29"}',"value":"2016-11-29"');
//
//    myCheckSuccess('{"id":"HZGDVIIDTbsRPoQUJA5Bo9e9f3Xfla8O_STARTDATE","name":"2016-11-28"}',"value":"2016-11-28"');

//      myCheckSuccess('{"id":"HZGDVIIDTbsRPoQUJA5Bo9e9f3Xfla8O_STARTDATE","name":"NAME"',"value":"601"}');
//}
/**
 *
 * @param dataStr
 */
function myCheckSuccess(dataStr){
    var myobj;
    try{
        myobj=JSON.parse(dataStr);
    }catch(e){
        // alert(dataStr);
         alert("JSON字符串有误"+e);
        return ;
    }
    assignValue(myobj.id,myobj.name,myobj.value);

}
/**
 * 给表单id赋值
 * @param key
 * @param value
 */
function assignValue(mykey,showvalue,myvalue){
    if(document.getElementsByName(mykey).length>0) {//单选，多选，下啦等
            for(var j=0;j<document.getElementsByName(mykey).length;j++ ){
                if(document.getElementsByName(mykey)[j].value == myvalue){
                    document.getElementsByName(mykey)[j].checked = true; //选中
                }
            }
    }else if(checkEmpty(document.getElementById(mykey))){
        if(!checkEmpty(showvalue)){
        showvalue = '';
        }
        document.getElementById(mykey).setAttribute('realvalue',myvalue);
        //var valueData =  document.getElementById(mykey).value;
        document.getElementById(mykey).value = showvalue;
    }
    _onChange();
}
function _onChange(){}
/**
 * 提交动作
 */
function doSubmit(mAction){
    mAction = checkValue(mAction);
    var datastr = submitData(mAction);

    HTStartform.submitData(datastr,submitSuccess);
}
/**
 * 提交成功回调
 */
function submitSuccess(){
    //alert("submitSuccess");
}

/**
 * 提交返回值
 */
function submitData(mAction){
    //检查必填字段是否填写
    var checkMsg = checkMustFields();

    var info;
    var resultdata = "";
    if ( checkEmpty(checkMsg) ){
//            dialog('当前表单提示信息',"表单字段："+checkMsg+" 为必填字段，请填写后再提交。");
        resultdata = '{"result":"1",';
        resultdata += '"resultMsg":"必填字段：'+checkMsg+'尚未填写！",';
        resultdata += '"resultAction":"'+mAction+'",';
        resultdata += '"resultInfo":""}';
    }else{

        //获取编辑字段值
        var resultInfo = getEditFields();
        resultdata = '{"result":"0",';
        resultdata += '"resultMsg":"校验成功",';
        resultdata += '"resultAction":"'+mAction+'",';
        resultdata += '"resultInfo":'+resultInfo+'}';
    }
    //alert(resultdata);
    //dialog('当前表单提交信息',resultdata);
    return resultdata;
}
/**
 * 检测必填字段
 * @returns {*}
 */
function checkMustFields(){

    if(checkEmpty(mustInputClumn)){
        var strs= new Array(); //定义一数组
        var bool;
        var myvalue,namedesc;
        var mylenght;
        var key,input;
        strs=mustInputClumn.split("|"); //字符分割
        for (var i=0;i<strs.length ;i++ ){//分割后的字符输出
            if(checkEmpty(strs[i])){

                key = strs[i].substring(0,strs[i].indexOf(";"));

                if(checkEmpty(key)){
                    input = strs[i].substring(strs[i].indexOf(";")+1);
                    //alert("key===="+key+"======input===="+input);
                    //添加判断，下拉列表
                    if(input == HT_Event.htrf_select_id||
                        input == HT_Event.htrf_select_name||
                        input == HT_Event.htrf_select_value){

                        myvalue = document.getElementById("select_info_"+key).getAttribute("data-value");
                        namedesc = document.getElementById("select_info_"+key).getAttribute("namedesc");
                        if(!checkEmpty(myvalue)){//为空，没有填写
                            return namedesc;
                        }

                    }else if(input == HT_Event.htrf_select_input){//可编辑
                        myvalue = document.getElementById("select_info_"+key).value;
                        namedesc = document.getElementById("select_info_"+key).getAttribute("namedesc");
                        if(myvalue == "请选择" ||!checkEmpty(myvalue) ){//为空，且是否存在下拉列表中，不存在则为手输入
                            return namedesc;
                        }
                    } else if(document.getElementsByName(key).length>0){//单选，多选
                        bool = false;
                        for(var j=0;j<document.getElementsByName(key).length;j++ ){
                            if(!bool){
                                namedesc = document.getElementsByName(key)[j].getAttribute("namedesc");
                                if(document.getElementsByName(key)[j].checked){
                                    bool=true;
                                }
                            }
                        }
                        if(!bool){
                            return namedesc;
                        }

                    }else if(checkEmpty(document.getElementById(key))){
                        myvalue =  document.getElementById(key).value;
                        namedesc = document.getElementById(key).getAttribute("namedesc");
                        if(!checkEmpty(myvalue)){
                            mylenght = document.getElementById(key).innerText.trim().length;
                            if(mylenght<=0){
                                return namedesc;
                            }
                        }
                    }else{

                    }
                }
            }
        }
    }
    return "";
}
/**
 * 获取编辑字段的值
 * @returns {*}
 */
function getEditFields(){

    if(checkEmpty(editClumn)){

        var strs= new Array(); //定义一数组
        var myvalue;
        var editFieldId,input;
        strs=editClumn.split("|"); //字符分割

        var dataStr='{"formId":"'+formId+'",';
        dataStr += '"formInfo":[';
        for (var i=0;i<strs.length ;i++ ) {//分割后的字符输出
            if(checkEmpty(strs[i])){
                editFieldId = strs[i].substring(0,strs[i].indexOf(";"));
                input = strs[i].substring(strs[i].indexOf(";")+1);
                dataStr += '{"key":"'+strs[i].substring(strs[i].indexOf("_")+1,strs[i].indexOf(";"))+'",';
                dataStr += '"input":"'+input+'",';
                dataStr += '"formKey":"'+formId+'",';

                if(input == HT_Event.htrf_select_id||
                    input == HT_Event.htrf_select_name||
                    input == HT_Event.htrf_select_value){
                    myvalue = document.getElementById("select_info_"+editFieldId).getAttribute("data-value");
                }else if(input == HT_Event.htrf_select_input){//可编辑
                    //
                    myvalue = document.getElementById("select_info_"+editFieldId).value;
                    alert("checkChildNodes===="+checkChildNodes(myvalue,"options_"+editFieldId));
                    if(checkChildNodes(myvalue,"options_"+editFieldId)){//存在，曲直data－value
                        myvalue = document.getElementById("select_info_"+editFieldId).getAttribute("data-value");
                    }
                    if(!checkEmpty(myvalue)){//为空，且是否存在下拉列表中，不存在则为手输入
                        myvalue = document.getElementById("select_info_"+editFieldId).getAttribute("data");
                    }
                }else if(document.getElementsByName(editFieldId).length>0){//单选，多选，下啦等
                    myvalue = "";
                    for(var j=0;j<document.getElementsByName(editFieldId).length;j++ ){

                        if(document.getElementsByName(editFieldId)[j].checked){
                            myvalue+=document.getElementsByName(editFieldId)[j].value+";";
                        }
                    }
                    if(document.getElementsByName(editFieldId).length>0 && checkEmpty(myvalue)){
                        myvalue = myvalue.substring(0,myvalue.length-1);
                    }
                }else if(checkEmpty(document.getElementById(editFieldId))){
                    if(checkEmpty(document.getElementById(editFieldId).getAttribute("realvalue"))){
                        myvalue = document.getElementById(editFieldId).getAttribute("realvalue");
                    }else  if (checkEmpty(document.getElementById(editFieldId).value)){
                        myvalue =  document.getElementById(editFieldId).value;
                    }else {
                        myvalue = document.getElementById(editFieldId).innerText.trim();
                    }
                }
                dataStr += '"value":"'+ checkValue(myvalue) +'"},';
            }

        }
        if(strs.length>0){
            dataStr = dataStr.substring(0,dataStr.length-1);
        }

        dataStr += ']}';
        return dataStr;
    }
    return "";
}
/**
 * 判断内容是否存在于选择项中
 * @param str
 * @param nodeId
 * @returns {boolean}
 */
function checkChildNodes(str,nodeId){
    try{
        var myvalue;
        for(var i=0 ; i<document.getElementById(nodeId).childNodes.length;i++ ){
           myvalue = document.getElementById(nodeId).childNodes[i].innerHTML;
            if(myvalue == str){
                return true;
            }
        }
        return false;
    }catch(e){
        return false;
    }

}

/**
 * 检验字符串是否为空
 * @param str
 * @returns {boolean}
 */
function checkEmpty(str){
    try{
        if(typeof(str) !='undefined' && str != "undefined" && str!="" &&  str != null && str != "null"){
            return true;
        }
        return false;
    }catch(e){
        return false;
    }
}
/**
 * 空值 返回空
 * @param str
 * @returns {*}
 */
function checkValue(str){
    try{
        if(typeof(str) =='undefined' || str == "undefined" || str =="" ||  str == null || str == "null"){
            return "";
        }
        return str;
    }catch(e){
        return "";
    }

}


/**
 * 计算多行输入长度  textarea
 * @param myElementObj
 * @param myRest
 * @param num
 */
function excute_textarea(myElementObj,myRest,num){
    var num1 =  myElementObj.value.length;
    if(checkEmpty(num)){
        if(num1 > num){
            myElementObj.value = myElementObj.value.substring(0,num);
            num1 = num;
            document.body.onfocus();
//              showMsg('表单字段过长','当前字段长度超出允许输入的长度，不能编写');
        }
        var rest = num - num1;
        document.getElementById(myRest).innerHTML = rest+ "/"+num;
    }
}
/**
 * 计算多行输入长度  div
 * @param myElementObj
 * @param myRest
 * @param num
 */
function excute_div(myElementObj,myRest,num){
    var num1 = myElementObj.innerText.trim().length;
    if(checkEmpty(num)){
        if(num1 > num){
            myElementObj.innerText = myElementObj.innerText.substring(0,num);
            num1 = num;
            var sel = window.getSelection();
            var range = document.createRange();
            range.selectNodeContents(myElementObj);
            range.collapse(false);
            sel.removeAllRanges();
            sel.addRange(range);
//              showMsg('表单字段过长','当前字段长度超出允许输入的长度，不能编写');
        }
        var rest = num - num1;
        document.getElementById(myRest).innerHTML = rest+ "/"+num;
    }
}
/**
 * 改变tr显示隐藏
 * @param changeTrClass
 */
function changeTr(changeTrClass) {
    if ($(changeTrClass).css('display') == 'none') {
        $(changeTrClass).removeAttr("style");
    } else {
        $(changeTrClass).css("display", "none");
    }
}
/**
 * 显示隐藏
 * @param changeTrClass
 */
function showAndHidden(myobj){


    var trs = document.getElementsByName(myobj);
    if(trs.length>0){
        for(var trsi = 0; trsi < trs.length ; trsi++){
            if(trs[trsi].style.display == 'block'){
                trs[trsi].style.display = 'none';
            }else{
                trs[trsi].style.display = 'block';
            }
        }
//            return ;
    }
    var div1=document.getElementById(myobj);
    if(div1.style.display=='block'){
        div1.style.display='none';
    }
    else{
        div1.style.display='block';
    }
    mydomain();
}
/**
 * 显示隐藏
 * 测试
 */
function showAndHidden2(){
    var div3=document.getElementById("div3");
    var div4=document.getElementById("div4");
    if(div3.style.visibility=='visible') div3.style.visibility='hidden';
    else div3.style.visibility='visible';
    if(div4.style.visibility=='visible') div4.style.visibility='hidden';
    else div4.style.visibility='visible';
}
/**
 * 返回表单页面内容
 */
function formInfoButton() {
    alert('document.body>>>>>>>>>'+document.body.innerHTML);
}

function getArray(myArray){
    if(myArray!="undefined"&& myArray!=""){
        for(var i=0;i<myArray.length;i++){
            myArray[i] = myArray[i]*2;
        }
    }
    return myArray;
}
function array_max( )
{
    var i, max = this[0];
    for (i = 1; i < this.length; i++)
    {
        if (max < this[i])
            max = this[i];
    }
    return max;
}

function text(){
    //var my = getArray(new Array(2,4,6));
    Array.prototype.max = array_max;
    var x = new Array(1, 2, 3, 4, 5, 6);
    var y = x.max( );
}
