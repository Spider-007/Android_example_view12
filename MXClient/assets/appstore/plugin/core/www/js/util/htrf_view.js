/**
 * htrf_view js V1.0.0
 * Created by xiaoheidepro on 16/12/5.
 *
 */

/*#############################################################
 Name: Select to CSS
 Version: 0.2
 Author: Utom
 URL: http://utombox.com/
 #############################################################*/
var selects = document.getElementsByTagName('select');

var isIE = (document.all && window.ActiveXObject && !window.opera) ? true : false;

function $(id) {
    return document.getElementById(id);
}

function stopBubbling (ev) {
    ev.stopPropagation();
}
//初始化下拉列表
function rSelects() {
    for (i=0;i<selects.length;i++){
        selects[i].style.display = 'none';
        select_tag = document.createElement('div');
        select_tag.id = 'select_' + selects[i].name;
        select_tag.className = 'select_box';
        selects[i].parentNode.insertBefore(select_tag,selects[i]);

        edit = selects[i].getAttribute("edit");

        select_info = document.createElement('input');
        select_info.id = 'select_info_' + selects[i].name;
        select_info.className='weui-input';
        //select_info.style.cursor='pointer';
        if(edit!="undefined" && edit!="null" && edit == "true"){//可编辑
        }else{
            select_info.setAttribute("readonly","true");
        }
        select_info.setAttribute("type","text");
        select_info.setAttribute("onkeyup"," keyup_select('"+select_info.id+"') ");
        select_tag.appendChild(select_info);

        select_ul = document.createElement('ul');
        select_ul.id = 'options_' + selects[i].name;
        select_ul.className = 'tag_options';
        select_ul.style.position='absolute';
        select_ul.style.display='none';
        select_ul.style.zIndex='999';
        select_tag.parentNode.parentNode.parentNode.parentNode.appendChild(select_ul);
        if(selects[i].options.length>5){//设置滚动条及高度
            select_ul.style.overflowY="scroll";
            select_ul.style.height="100px";
        }
        rOptions(i,selects[i].name);

        mouseSelects(selects[i].name);

        if (isIE){
            selects[i].onclick = new Function("clickLabels3('"+selects[i].name+"');window.event.cancelBubble = true;");
        }
        else if(!isIE){
            selects[i].onclick = new Function("clickLabels3('"+selects[i].name+"')");
            selects[i].addEventListener("click", stopBubbling, false);
        }
        //删除该标签，不能删除，删除后不可用
        //selects[i].parentNode.removeChild(selects[i]);

    }
}

//选择项初始化
function rOptions(i, name) {
    var options = selects[i].getElementsByTagName('option');
    var options_ul = 'options_' + name;
    for (n=0;n<selects[i].options.length;n++){
        option_li = document.createElement('li');
        option_li.style.cursor='pointer';
        option_li.className='open';
        $(options_ul).appendChild(option_li);

        option_text = document.createTextNode(selects[i].options[n].text);
        option_li.appendChild(option_text);

        option_selected = selects[i].options[n].selected;
        //实际值
        option_li.setAttribute("value",selects[i].options[n].value);

        if(option_selected){
            option_li.className='open_selected';
            option_li.id='selected_' + name;
            $('select_info_' + name).setAttribute("data-value",option_li.getAttribute("value"));
            $('select_info_' + name).setAttribute("value",option_li.innerHTML);
        }

        option_li.onmouseover = function(){	this.className='open_hover';}
        option_li.onmouseout = function(){
            if(this.id=='selected_' + name){
                this.className='open_selected';
            }
            else {
                this.className='open';
            }
        }
        option_li.onclick = new Function("clickOptions("+i+","+n+",'"+selects[i].name+"')");
    }

}
//鼠标选择
function mouseSelects(name){
    var sincn = 'select_info_' + name;

    //$(sincn).onmouseover = function(){ if(this.className=='tag_select') this.className='tag_select_hover'; }
    //$(sincn).onmouseout = function(){ if(this.className=='tag_select_hover') this.className='tag_select'; }

    if (isIE){
        $(sincn).onclick = new Function("clickSelects('"+name+"');window.event.cancelBubble = true;");
    }
    else if(!isIE){
        $(sincn).onclick = new Function("clickSelects('"+name+"');");
        $('select_info_' +name).addEventListener("click", stopBubbling, false);
    }

}
//点击选择中项
function clickSelects(name){
    var sincn = 'select_info_' + name;
    var sinul = 'options_' + name;
    for (i=0;i<selects.length;i++){

        if(selects[i].name == name){
            if( $(sinul).style.display == 'none'){
                $(sinul).style.display = '';
            }else{
                $(sinul).style.display = 'none';
            }
            //if( $(sincn).className =='tag_select_hover'){
            //    $(sincn).className ='tag_select_open';
            //    $(sinul).style.display = '';
            //} else if( $(sincn).className =='tag_select_open'){
            //    $(sincn).className = 'tag_select_hover';
            //    $(sinul).style.display = 'none';
            //}
        }
        else{
            $('select_info_' + selects[i].name).className = 'weui-input';
            $('options_' + selects[i].name).style.display = 'none';
        }
    }

}
//点击选择项
function clickOptions(i, n, name){
    var li = $('options_' + name).getElementsByTagName('li');
    //var selectParentId = $('select_info_' + name).parentNode.id;
    var selectParent = $('select_info_' + name).parentNode;
    $('selected_' + name).className='open';
    $('selected_' + name).id='';
    li[n].id='selected_' + name;
    li[n].className='open_hover';
    $('select_info_' + name).parentNode.removeChild($('select_info_' + name));
    //$('select_' + name).removeChild($('select_info_' + name));
    select_info = document.createElement('input');
    select_info.id = 'select_info_' + name;
    select_info.className='weui-input';
    select_info.style.cursor='pointer';
    select_info.setAttribute("type","text");
    select_info.setAttribute("data-value",li[n].getAttribute("value"));
    select_info.setAttribute("value",li[n].innerHTML);

    edit = selects[i].getAttribute("edit");
    if(edit!="undefined"&&edit!="null"&&edit=="true"){//可编辑
    }else{
        select_info.setAttribute("readonly","true");
    }
    //$(selectParent).appendChild(select_info);
    selectParent.appendChild(select_info);

    mouseSelects(name);
    $( 'options_' + name ).style.display = 'none' ;
    $( 'select_info_' + name ).className = 'weui-input';
    selects[i].options[n].selected = 'selected';

}

function keyup_select(id){
   // alert("data====="+$( id).getAttribute("value"));
   // alert("data====="+$( id).getAttribute("data-value"));
}
