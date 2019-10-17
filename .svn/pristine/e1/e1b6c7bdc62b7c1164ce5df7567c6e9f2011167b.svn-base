/**
 * Created by xiaoheidepro on 16/9/29.
 */
document.write(' <script src="HTEvent_config.js"> <\/script>');

var HT_WEUI_view = HT_WEUI_view || {};

HT_WEUI_view={
    /**
     * 文本类型
     * @param item
     * @returns {string}
     */
    text : function(item,dics){
        var str  = "<div class=\"weui-cells\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui-cell__bd\">";
            str += "<input class=\"weui-input\" type=\"text\"  ";
            str += " id ='"+formId+"_"+item.Key+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += "value = '"+item.Value+"' ";
            str += item.isReadOnly? " readonly = readonly " :(dics!="undefined" && dics !="" ? " onclick= 'sendDic(this,"+dics+")' readonly = readonly ":"");
            //str += dics!="undefined" && dics !="" ? " onclick= 'sendDic(this,"+dics+")' readonly = readonly ":(item.isReadOnly?" readonly = readonly " :"");
            str += item.MustInput?" required='required'":"";
            str += "/>";
            str += "</div>";
            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += "</div>";


        return str;
    },

    text_multi : function(item){

        var str  = "<div class=\"weui-cells weui-cells_form\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui-cell__bd\">";
            str += "<textarea class=\"weui-textarea\"   ";
            //str +=  item.rowsNum!='underfined' && item.rowsNum >0 ? "rows= \""+item.rowsNum +"\"" :" rows= \""+HTEvent_config.text_multi_row +"\"";


            str += " rows= \"3\"   ";
            str += " id ='"+formId+"_"+item.Key+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += item.isReadOnly?" readonly=readonly " :"";
            str += item.Dics?" onclick= 'sendDic(this.id)'":"";
            str += item.MustInput?" required='required'":"";
            str += " onkeyup=\"excute_textarea(this,'"+formId+"_"+item.Key+"_rest',"+item.length+")\" onkeydown=\"excute_textarea(this,'"+formId+"_"+item.Key+"_rest',"+item.length+")\" ";
            str += " >";
            str += item.Value;
            str += "</textarea>";
            str += "</div>";
            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += "</div>";
            str += item.isReadOnly? "":" <div class=\"weui-textarea-counter\" id=\""+formId+"_"+item.Key+"_rest\" > "+(item.length*1 - item.Value.length*1) +"/"+item.length+"</div>";
        return str;
    },
    text_editable : function(item){

        var str  = "<div class=\"weui-cells weui-cells_form\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui-cell__bd\">";
            str += item.isReadOnly? " <div contenteditable=\"false\" " :" <div contenteditable=\"true\" ";
            str += " id ='"+formId+"_"+item.Key+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += " class=\"weui-textarea\"  ";
            str += item.MustInput?" required='required'":"";
            str += " maxlength = "+item.length;
            str += " onkeyup=\"excute_div(this,'"+formId+"_"+item.Key+"_rest',"+item.length+")\" onkeydown=\"excute_div(this,'"+formId+"_"+item.Key+"_rest',"+item.length+")\">";
            str += item.Value;
            str += "</div>";
            str += "</div>";
            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += "</div>";
            str += item.isReadOnly? "":" <div class=\"weui-textarea-counter\" id=\""+formId+"_"+item.Key+"_rest\" > " + (item.length*1 - item.Value.length*1)  +"/"+item.length+"</div>";

        return str;
    },
    /**
     * 数字类型
     * @param item
     * @returns {string}
     */
    text_num : function(item,dics){
        var id = formId+"_"+item.Key;
        var str  = "<div class=\"weui-cells\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui-cell__bd\">";
            str += "<input class=\"weui-input\" type=\"number\"  ";
            str += " id ='"+id+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += " value = '"+item.Value+"' ";
            if(item.isReadOnly){
                str += " readonly = readonly ";
            }else if(dics!="undefined" && dics !=""){
                if(dics==HT_Event.htrf_num_Integer){
                    str += " onkeyup=\"keyup_integer('"+id+"')\"  onfocus=\"integer_star(this)\" onblur=\"integer_end(this)\" ";
                }else if(dics==HT_Event.htrf_num_decimal){
                    str += " onkeyup=\"keyup_decimal('"+id+"')\"  onfocus=\"decimal_star(this)\" onblur=\"decimal_end(this)\" ";
                }else if(dics==HT_Event.htrf_num_Integer_thousands){
                    str += " onkeyup=\"keyup_integer('"+id+"')\"  onfocus=\"integer_star(this)\" onblur=\"integer_end(this)\" ";
                }else if(dics==HT_Event.htrf_num_decimal_thousands){
                    str += " onkeyup=\"keyup_decimal('"+id+"')\"  onfocus=\"decimal_star(this)\" onblur=\"decimal_end(this)\" ";
                }
            }
            str += item.MustInput?" required='required'":"";
            str += "/>";
            str += "</div>";
            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += "</div>";


            return str;
    },
    /**
     * 文本隐藏类型
     * @param item
     * @returns {string}
     */
    hidden : function(item){
                var str="<input ";
                str+="id ='"+formId+"_"+item.Key+"' " ;
                str+="name ='"+item.Key+"' ";
                str += " namedesc ='"+item.Name+"'";
                str+="type ='hidden'" ;
                str+="value = '"+item.Value+"' readonly=readonly ";
                str+=item.MustInput?" required='required'":"";
                str+="/>";
                return str;
    },
    /**
     * 文本隐藏类型
     * @param item
     * @returns {string}
     */
    hiddenStr : function(key,value){
        var str="<input ";
        str+="id ='"+key+"' " ;
        str+="name ='"+key+"' ";
        str+="type ='hidden'" ;
        str+="value = '"+value+"' readonly=readonly "; str+="/>";
        return str;
    },
    /**
     * 单选按钮类型
     * @param item
     * @returns {string}
     */
    button : function(item){




        var str ="<div class=\"weui-btn-area\"> ";
            str +=" <a class=\"weui-btn weui-btn_primary\" " ;
            str +=" id ='"+formId+"_"+item.Key+"' " ;
            str +=" name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += item.Dics?" onclick = 'initButton("+JSON.stringify(item)+")'":"";
            str +=" >" ;
            str +=item.Value;
            str +="</a> ";
            str +="</div> ";

        //var str="<button id='myBtn' onclick='initButton()'>点我<\/button>";
        return str;
    },
    /**
     * 单选按钮类型
     * @param item
     * @returns {string}
     */
    weuiradio : function(item){




        var str  = "<div class=\"weui-cells weui-cells_radio\"  >";

        if(item.Selections!=null && item.Selections!='underfind'&&item.Selections.length>0){
            for(i=0 ; i<item.Selections.length;i++){

                var name = formId+"_"+item.Key;
                var id = name+"_"+i;



                str += " <label class=\"weui-cell weui-check__label\" for=\"" + id + "\"> ";

                str += " <div class=\"weui-cell__bd\">";
                str += " <p>";
                str += item.Selections[i].selectValue;
                str += "</p>";
                str += " </div>";
                str += " <div class=\"weui-cell__ft\">";
                str += weuiradioItem(id,name,item.Selections[i],item.Value,item.Name);
                str += " <span class=\"weui-icon-checked\"></span>";
                str += " </div>";
                str += " </label> ";

            }
        }
            str +="</div>";
        return str;
    },
    /**
     * 单选按钮类型
     * @param item
     * @returns {string}
     */
    radio : function(item){
        var str  = "<div class=\"weui-cells weui-cells_form\">";
            str += "<div class=\"weui-cell-read\">";
            str += "<div class=\"weui-cell__bd\">";
            if(item.Selections!=null && item.Selections!='underfind'&&item.Selections.length>0){
                for(i=0 ; i<item.Selections.length;i++){
                    str += radioItem(formId+"_"+item.Key,item.Selections[i],item.Value,item.Name);
                }
            }
            str += " </div>";
            str += " </div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += " </div>";
        return str;
    },
    /**
     * 多选按钮类型
     * @param item
     * @returns {string}
     */
    checkbox : function(item){

        var str  = " <div class=\"weui-cells weui-cells_checkbox\">";
        if(1==2){//是否只读
            return  item.value;
        }else{
            if(item.Selections!=null && item.Selections!='underfind'&&item.Selections.length>0){
                for(i=0 ; i<item.Selections.length;i++){
                    var name = formId+"_"+item.Key;
                    var id = name+"_"+i;

                    str += "<label class=\"weui-cell weui-check__label\" for=\"" + id + "\">";
                    str += " <div class=\"weui-cell__hd\">";
                    str += checkboxItem(id,name,item.Selections[i],item);
                    str += " <i class=\"weui-icon-checked\"></i>";
                    str += " </div>";
                    str += " <div class=\"weui-cell__bd\">";
                    str += " <p>";
                    str += item.Selections[i].selectValue;
                    str += "</p>";
                    str += " </div>";
                    str += " </label>";
                }
            }
        }
            str += " </div>";
        return str;
    },
    checkbox_switch :  function(item){

        var str  = "<div class=\"weui-cells weui-cells_form\"> ";
        if(1==2){//是否只读
            return  item.value;
        }else{
            if(item.Selections!=null && item.Selections!='underfind'&&item.Selections.length>0){
                for(i=0 ; i<item.Selections.length;i++){
                    var name = formId+"_"+item.Key;
                    var id = name+"_"+i;
                    str += "<div class=\"weui-cell weui-cell_switch\">";
                    str += " <div class=\"weui-cell__bd\">";
                    str += item.Selections[i].selectValue;
                    str += "</div>";
                    str += checkbox_switchItem(id,name,item.Selections[i],item);
                    str += " <div class=\"weui-cell__ft\">";
                    str += " </div>";
                    str += " </div>";
                }
            }
        }
        str += " </div>";
        return str;
    },
    /**
     * 下拉列表单选类型
     * @param item
     * @returns {string}
     */
    select : function(item){

        var str  = "<div class=\"weui-cells\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell_select_cell \">";
            if(item.isReadOnly){//只读，显示样式为文本
                str += "<div class=\"weui-cell__bd\">";
                str += "<input class=\"weui-input\" type=\"text\"  ";
                str += " id ='"+formId+"_"+item.Key+"' " ;
                str += " name ='"+item.Key+"' ";
                str += " namedesc ='"+item.Name+"'";
                str += " value = '"+item.Value+"' ";
                str += " readonly = readonly ";
                str += "/>";
                str += "</div>";
            }else{
                str += "<div class=\"weui-cell_select_bd\">";
                str += "<select name=\""+formId+"_"+item.Key+"\" ";
                str += " class=\"weui-select\" ";

                if(item.Input ==HT_Event.htrf_select_input){//可编辑
                    str += " edit=\"true\" ";
                }
                str += " id=\""+formId+"_"+item.Key+"\" ";
                str += " onchange=\"show(this)\">";
                if(item.Selections!=null && item.Selections!='undefined'&&item.Selections.length>0){
                    var myselection = new Object();
                    myselection.dataValue="";
                    myselection.selectValue="请选择";
                    str += selectItem(myselection,item.Value);
                    var flag = false;//不包含该字段
                    for(var i=0 ; i<item.Selections.length;i++){
                        str += selectItem(item.Selections[i],item.Value);
                        if(item.Value == item.Selections[i].dataValue){
                            flag = true;
                        }
                    }
                    if(!flag){//不存在
                        myselection.dataValue=item.Value;
                        myselection.selectValue=item.Value;
                        str += selectItem(myselection,item.Value);
                    }
                }

                str += "<\/select>";
                str += "</div>";

            }
            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
        return str;
    },
    /**
     * 下拉列表单选类型
     * @param item
     * @returns {string}
     */
    select_slide : function(item){
        var str="<select name='"+formId+"_"+item.Key+"' ";
        str+="id='"+formId+"_"+item.Key+"' ";
        str+="  >";
        if(item.Selections!=null && item.Selections!='undefined'&&item.Selections.length>0){
            for(i=0 ; i<item.Selections.length;i++){
                str += selectItem_slide(item.Selections[i],item.Value);
            }
        }
        str+="<\/select>";
        return str;
    },
    /**
     * 日期选择类型
     * @param item
     * @returns {string}
     */
    date : function(item){

        var str  = "<div class=\"weui-cells weui-cells_form\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui-cell__bd\">";
            str += "<input class=\"weui-input\" type=\"date\"  ";
            str += " id ='"+formId+"_"+item.Key+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += " value = '"+item.Value+"' ";
            str += item.isReadOnly?" readonly=readonly " :"";
            //str += item.Dics?" onclick= 'sendDic(this.id)'":"";
            str += item.MustInput?" required='required'":"";
            str += " placeholder=\"\" />";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";

            str += "</div>";
            str += "</div>";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += "</div>";
            str += "</div>";

        return str;
    },
    /**
     * 日期时间选择类型
     * @param item
     * @returns {string}
     */
    datetime : function(item){


        var str  = "<div class=\"weui-cells weui-cells_form\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui-cell__bd\">";
            str += "<input class=\"weui-input\" type=\"datetime-local\"  ";
            str += " id ='"+formId+"_"+item.Key+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += " value = '"+item.Value+"' ";
            str += item.isReadOnly?" readonly=readonly " :"";
            //str += item.Dics?"onclick= 'sendDic(this.id)'":"";
            str += item.MustInput?" required='required'":"";
            str += " placeholder=\"\" />";

            str += "</div>";

            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += "</div>";
        return str;
    },
    /**
     * 日期选择类型
     * @param item
     * @returns {string}
     */
    weuidate : function(item){


        var str  = "<div class=\"weui-cells weui-cells_form\">";
            str += item.isReadOnly? "<div class=\"weui-cell-read\">":"<div class=\"weui-cell\">";
            str += "<div class=\"weui_cell_bd weui_cell_primary\">";
            str += "<input class=\"weui-input\" type=\"text\"  ";
            str += " id ='"+formId+"_"+item.Key+"' " ;
            str += " name ='"+item.Key+"' ";
            str += " namedesc ='"+item.Name+"'";
            str += " value = '"+item.Value+"' ";
            str += item.isReadOnly?" readonly=readonly " :"";
            //str += item.Dics?" onclick= 'sendDic(this.id)'":"";
            str += item.MustInput?" required='required'":"";
            str += " placeholder=\"\" />";
            str += "</div>";
            str += "</div>";
            str += "<div id=\""+formId+"_"+item.Key+"_suffix\">";
            str += item.isReadOnly? "":(item.MustInput?"<font color=red>*</font>":"");
            str += "</div>";
            str += "</div>";

        return str;
    },



}

//$(function(){
//    var my = myOpinion.prototype;
//    my.init($(".z-sidebar li em"),$("#contact"));
//    $("#contact").add(my.closeWindow($(".z-sidebar li em"),$("#contact")));
//});


/**
 * 单选按钮每个选项内容
 * @param item
 * @returns {string}
 */
function weuiradioItem(id,name ,selection,itemValue,namedesc){
    var str  = "<input ";
        str += "id =\""+id+"\" " ;
        str += "name =\""+name+"\" " ;
        str += " namedesc ='"+namedesc+"'";
        str += "type =\"radio\" " ;
        str += "class=\"weui-check\" ";
        str += "value = \""+selection.dataValue +"\" ";
        str += "onclick=\"show(this)\" ";
    if(itemValue==selection.dataValue){
        str+=" checked='true' ";
    }
    str+="/>";
    return str;
}

/**
 * 单选按钮每个选项内容
 * @param item
 * @returns {string}
 */
function radioItem(name ,selection,itemValue,namedesc){
    var str  =  "<label class=\"weui-cells-select\"><input ";
        str +=  " name ='"+name+"' " ;
        str +=  " namedesc ='"+namedesc+"'";
        str +=  " type ='radio' " ;
        str +=  " value = '"+selection.dataValue +"' ";
        str +=  " onclick='show(this)' ";
    if(itemValue==selection.dataValue){
        str+=" checked='true' ";
    }
    str+="/>"
    str+="<span margin-left=\"1em\" >";
    str+=selection.selectValue;
    str+="</span>";
    str+="</label>";
    str+="";
    return str;
}



/**
 * 多选按钮每个选项内容
 * @param item
 * @returns {string}
 */
function checkboxItem(id,name ,selection,item){

    var str  = "<input ";
        str += "id =\""+id+"\" " ;
        str += "name =\""+name+"\" " ;
        str += " namedesc ='"+item.Name+"'";
        str += "type =\"checkbox\" " ;
        str += "class=\"weui-check\" " ;
        str += "value = '"+selection.dataValue +"' ";
    if(item.Value !=''&& item.Value !=null && item.Value!='undefined' ){
        var itemValue = ";"+item.Value+";";
        var mydataValue = ";"+selection.dataValue+";";

        if(itemValue.indexOf(mydataValue)>-1){//表示存在
            str+=" checked='true' ";
        }
    }
    str+=item.isReadOnly?" onclick= 'return false'  disabled = 'disabled' " :" onclick='show(this)' ";
    str+="/>";
    return str;
}

function checkbox_switchItem(id,name ,selection,item){

    var str  = "<input ";
        str += " id =\""+id+"\" " ;
        str += " name =\""+name+"\" " ;
        str += " namedesc ='"+item.Name+"'";
        str += " type =\"checkbox\" " ;
        str += " class=\"weui-switch\" " ;
        str += " value = '"+selection.dataValue +"' ";
    if(item.Value !=''&& item.Value !=null && item.Value!='undefined' ){
        var itemValue = ";"+item.Value+";";
        var mydataValue = ";"+selection.dataValue+";";

        if(itemValue.indexOf(mydataValue)>-1){//表示存在
            str+=" checked='true' ";
        }
    }
    str+=item.isReadOnly?" onclick= 'return false'  disabled = 'disabled' " :" onclick='show(this)' ";
    str+="/>";
    return str;
}



/**
 * 下拉选按钮每个选项内容
 * @param item
 * @returns {string}
 */
function selectItem(selection,itemValue){
    var str="<label><option  ";
    str+="value = '"+selection.dataValue +"' ";
    if(itemValue==selection.dataValue){
        str+=" selected='true' ";
    }
    str+=" >";
    str+=selection.selectValue;
    str+="</option>";
    str+="</label>";
    str+="</BR>";
    return str;
}


/**
 * 下拉选按钮每个选项内容
 * @param item
 * @returns {string}
 */
function selectItem_slide(selection,itemValue){
    var str="<option  ";
    str+="value = '"+selection.dataValue +"' ";
    if(itemValue==selection.dataValue){
        str+=" selected='true' ";
    }
    str+=" >";
    str+=selection.selectValue;
    str+="</option>";
    return str;
}
