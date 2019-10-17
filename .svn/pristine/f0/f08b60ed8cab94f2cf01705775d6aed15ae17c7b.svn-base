/**
 * Created by xiaoheidepro on 16/10/8.
 */
//document.write(' <script src="HTEvent_config.js"> <\/script>');
//document.write(' <script src="HTWEUI_view.js"> <\/script>');
var formId,nodeID;
var jsStr="";
var jsDoc="";
var jsdomianStr="";
var mustInputClumnStr="";
var editClumnStr="";
var pageTag= "";
var mustInputClumn,editClumn;
var hiddenStr= "";
var ClassOrg = {
    initData: function() {
        return function() {
            this.initialize.apply(this, arguments);
        }
    }
};


var HT_EventInit = ClassOrg.initData();

HT_EventInit.prototype = {
    /*根据FileId获取html标签*/
    initialize: function(dataobj) {

        if(pageTag !='undefined' && pageTag == "1") return;//对象不能为空
        if(dataobj.lenght <= 0) return;//对象不能为空

        jsStr= "  pageTag=\"1\";  rSelects(); ";

        //去掉隐藏
        this.appendJs("if(pageTag !='undefined' && pageTag == \"1\"){") ;
        this.appendJs(" var myElements = document.getElementsByTagName(\"div\");") ;
        this.appendJs(" for (var j=0;j<myElements.length;j++){ ") ;
        this.appendJs(" if(myElements[j].className == \"td_my_hidden\"){ ") ;
        this.appendJs(" myElements[j].removeAttribute(\"class\"); ") ;
        this.appendJs(" } ") ;
        this.appendJs(" }") ;
        this.appendJs(" }") ;

        this.str = this.readHTML();
        formId = dataobj.Result.FormId;
        nodeID = dataobj.Result.CurrentNodeID;
        for(j = 0; j < dataobj.Result.FormInfo.length ; j++){
            var item=dataobj.Result.FormInfo[j];
            var filecode=dataobj.Result.FormInfo[j].Key;
            var mykey = this.getMykey(filecode);
            if(this.exists(mykey)){//判断字段是否存在于表单
                var mhtml = this.buildInputTag(item);
                this.myReplace(mykey,mhtml);
            }else{//不存在，将该字段隐藏不显示
                hiddenStr +=  HT_WEUI_view.hiddenStr(formId+"_"+item.Key,item.Value);
            }

        }
        //隐藏系统字段
        this.hiddenObj(dataobj);

        this.myReplaceScriptDomain(jsdomianStr);
        if(jsdomianStr!="" && jsdomianStr!="underfined" && jsdomianStr.length>0){
            this.appendJs(" mydomain();") ;
        }
        this.appendJs(" mustInputClumn = \"");
        this.appendJs(mustInputClumnStr);
        this.appendJs("\"; ");
        this.appendJs(" editClumn = \"");
        this.appendJs(editClumnStr);
        this.appendJs("\"; ");
        this.myReplaceScript(jsStr);
        this.myReplaceScriptDoc(jsDoc);
        //alert("jsStr==="+jsStr);
        this.writeHTML(this.str);
    },
    exists :function(myStr){
        if(this.str.indexOf(myStr)>=0){//表示存在
            return true;
        }
        return false;
    },
    //隐藏系统字段
    hiddenObj: function(dataobj){
        //增加隐藏字段
             hiddenStr +=  HT_WEUI_view.hiddenStr("DocID",dataobj.Result.DocID);
             hiddenStr +=  HT_WEUI_view.hiddenStr("FormId",dataobj.Result.FormId);
             hiddenStr +=  HT_WEUI_view.hiddenStr("DataId",dataobj.Result.DataId);
             hiddenStr +=  HT_WEUI_view.hiddenStr("TableName",dataobj.Result.TableName);
             hiddenStr +=  HT_WEUI_view.hiddenStr("DocAttachmentID",dataobj.Result.DocAttachmentID);
             hiddenStr +=  HT_WEUI_view.hiddenStr("FlowId",dataobj.Result.FlowId);
             hiddenStr +=  HT_WEUI_view.hiddenStr("FlowName",dataobj.Result.FlowName);
             hiddenStr +=  HT_WEUI_view.hiddenStr("AuthorId",dataobj.Result.CurrentAuthorId);
             hiddenStr +=  HT_WEUI_view.hiddenStr("Author",dataobj.Result.CurrentAuthor);
             hiddenStr +=  HT_WEUI_view.hiddenStr("NodeID",dataobj.Result.CurrentNodeID);
             hiddenStr +=  HT_WEUI_view.hiddenStr("NodeName",dataobj.Result.CurrentNodeName);
             hiddenStr +=  HT_WEUI_view.hiddenStr("UserId",dataobj.Result.CurrentUserId);
             hiddenStr +=  HT_WEUI_view.hiddenStr("Username",dataobj.Result.CurrentUsername);
             hiddenStr +=  HT_WEUI_view.hiddenStr("TrackId",dataobj.Result.CurrentTrackId);
             hiddenStr +=  HT_WEUI_view.hiddenStr("Kind",dataobj.Result.Kind);

        this.myReplace("[@HIDDEN]",hiddenStr);
    },
    /*根据数据源生成预定义的input标签*/
    buildInputTag: function(item) {
        var myId = formId+"_"+item.Key;

        if(item.MustInput){
            this.appendMustInputClumn(formId+"_"+item.Key+";"+item.Input);
        }
        //if(nodeID == "Start"){
        //  将表单数据全部传值回去
        //}
        if(!item.isReadOnly){
            this.appendEditClumn(formId+"_"+item.Key+";"+item.Input);
        }
        var html="";
        if(item.Input == HT_Event.text){
            html= HT_WEUI_view.text(item,"");
        }else if(item.Input == HT_Event.hidden){
            html = HT_WEUI_view.hidden(item);
        }else if(item.Input == HT_Event.text_multi){
            html = HT_WEUI_view.text_multi(item);
        }else if(item.Input == HT_Event.text_editable){
            html = HT_WEUI_view.text_editable(item);
            this.appendJsDomian("document.getElementById(\""+formId+"_"+item.Key+"\").style.width = document.getElementById(\""+formId+"_"+item.Key+"\").parentElement.clientWidth + "+"\"px\"") ;
        }else if(item.Input == HT_Event.htrf_num_decimal||
                    item.Input == HT_Event.htrf_num_Integer||
                    item.Input == HT_Event.htrf_num_decimal_thousands||
                    item.Input == HT_Event.htrf_num_Integer_thousands){

            html = HT_WEUI_view.text_num(item,item.Input);
        }else if(item.Input == HT_Event.button){
            html = HT_WEUI_view.button(item);
        }else if(item.Input == HT_Event.select||
                 item.Input == HT_Event.htrf_select_id||
                 item.Input == HT_Event.htrf_select_name||
                 item.Input == HT_Event.htrf_select_value){
            html = HT_WEUI_view.select(item);
            this.appendJs(" if(document.getElementById(\"options_"+myId+"\")!=\"undefined\" &&");
            this.appendJs(" document.getElementById(\"options_"+myId+"\")!=\"null\" &&");
            this.appendJs(" document.getElementById(\"options_"+myId+"\")!=\"\"){");
            this.appendJs(" document.getElementById(\"options_"+myId+"\").style.width = ");
            //this.appendJs(" (document.getElementById(\"options_"+myId+"\").parentElement.firstChild.clientWidth*1-2)"+"+\"px\"");
            this.appendJs(" (document.getElementById(\"options_"+myId+"\").parentElement.clientWidth*1-2)"+"+\"px\"");
            this.appendJs(" } ");
        }else if(item.Input == HT_Event.htrf_select_input){//可编辑下拉
            html = HT_WEUI_view.select(item);
            this.appendJs(" if(document.getElementById(\"options_"+myId+"\")!=\"undefined\" &&");
            this.appendJs(" document.getElementById(\"options_"+myId+"\")!=\"null\" &&");
            this.appendJs(" document.getElementById(\"options_"+myId+"\")!=\"\"){");
            this.appendJs(" document.getElementById(\"options_"+myId+"\").style.width = ");
            this.appendJs(" (document.getElementById(\"options_"+myId+"\").parentElement.clientWidth*1-2)"+"+\"px\"");
            this.appendJs(" } ");
        }else if(item.Input == HT_Event.select_slide){
            html = HT_Event_view.select_slide(item);
            if(!HT_Event.select_slide_bool){
                HT_Event.select_slide_bool=true;
            }
        }else if(item.Input == HT_Event.radio||
                 item.Input == HT_Event.htrf_radio_id||
                 item.Input == HT_Event.htrf_radio_name||
                 item.Input == HT_Event.htrf_radio_value){
            html = HT_WEUI_view.radio(item);
        }else if(item.Input == HT_Event.weuiradio){
            html = HT_WEUI_view.weuiradio(item);
        }else if(item.Input == HT_Event.checkbox||
                 item.Input == HT_Event.htrf_checkbox_id||
                 item.Input == HT_Event.htrf_checkbox_name||
                 item.Input == HT_Event.htrf_checkbox_value){
            html = HT_WEUI_view.checkbox(item);
        }else if(item.Input == HT_Event.checkbox_switch){
            html = HT_WEUI_view.checkbox_switch(item);
        }else if(item.Input == HT_Event.date){
            html = HT_WEUI_view.date(item);
        }else if(item.Input == HT_Event.datetime){
            html = HT_WEUI_view.datetime(item);
        }else if(item.Input == HT_Event.weuidate){
            html = HT_WEUI_view.weuidate(item);
            if(!HT_Event.weuidate_bool){
                jsDoc += this.addJsDoc("js/jquery-weui.js");
                HT_Event.select_slide_bool=true;
            }
            if(!item.isReadOnly){
                this.appendJs("$(\"#"+formId+"_"+item.Key+"\").calendar({") ;
                this.appendJs("onChange: function (p, values, displayValues) {") ;
                this.appendJs("console.log(values, displayValues);") ;
                this.appendJs("}") ;
                this.appendJs("});") ;
            }
        }
        else{//字典类型
            html= HT_WEUI_view.text(item,item.Input);
        }
        return html;
    },
    //初始化标签
    myReplace: function(mykey,displayContent) {
        //var mykey = '[@'+column+']';
        this.str = this.str.replace(mykey, displayContent);
    },
    //初始化标签
    getMykey: function(column) {
        return '[@'+column+']';
    },
    //初始化标签
    myReplaceScript: function(displayContent) {
    this.str = this.str.replace('//[@SCRIPT]', displayContent);
    },
    //初始化标签
    myReplaceScriptDoc: function(displayContent) {
        this.str = this.str.replace('<!--[@SCRIPTDOC]-->', displayContent);
    },
    //初始化标签
    myReplaceScriptDomain: function(displayContent) {
        this.str = this.str.replace('//[@SCRIPT_DOMAIN]', displayContent);
    },

    //获取标签
    getInnerHTML: function() {

        return document.body.innerHTML;
    },
    //更新标签
    setInnerHTML: function(str) {
        //alert(str);
        document.body.innerHTML = str;
    },
    //整个表单内容
    readHTML: function() {
        //alert(str);
        var test = document.getElementsByTagName('html')[0].outterHtml;
        if(test=='underfined'||test==null || test==''){
            test =document.getElementsByTagName('HTML')[0].innerHTML;
        }
        return test;
    },
    //更新标签
    writeHTML: function(str) {
        var myStr = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        myStr+="<html xmlns=\"http://www.w3.org/1999/xhtml\">";
        myStr+=str;
        myStr+="</html>";


        document.write(myStr);

        //document.body.innerHTML = str;
    },
    appendJs :function(str) {
        jsStr += str;
    },
    appendJsDomian :function(str) {
        jsdomianStr += str;
    },
    jsToString :function() {
        return "<script type=\"text/javascript\">"+jsStr+"<\/script>";
    },
    addJsDoc :function( doc) {
        return "<script type=\"text/javascript\" src=\""+doc+"\"><\/script>";
    },
    //必填
    appendMustInputClumn : function(str){
        mustInputClumnStr += str + "|";
    },
    //可编辑
    appendEditClumn : function(str){
        editClumnStr += str +"|";
    }


};


