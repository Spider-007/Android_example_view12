package htmitech.com.componentlibrary.entity;


import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;


public class FieldItem implements Serializable {

    private String Name;
    private boolean NameVisible;
    private String SplitString;
    private boolean NameRN;
    private String Value;
    private String BeforeValueString = "";
    private String EndValueString = "";
    private int Percent;
    private int DisplayOrder;
    private String Align = "";
    private String Key;
    private String Mode;
    private String Input = "";
    private boolean MustInput; //是否必填
    private String FormKey;
    private int IsSplitWithLine;
    private int NameFontColor;
    private int ValueFontColor;
    private int MaxLength;
    private String fieldId;
    private int editable;
    private boolean isExt;
    private String dependFieldKey;
    private int BackColor;
    private TabEvent ajaxEvent;
    public List<opintion> opintions;
    public List<dic> Dics;
    private List<FormExtensionFiles> filedImages;
    private List<FormExtensionFiles> filedAudios;
    private List<FormExtensionFiles> filedVideos;
    public List<SignsList> signs;


    public class opintion implements Serializable {
        public String opinionText;
        public String userName;
        public String saveTime;
        public String UserID;
        public String LoginName;
        public String signPic;
        public String signPicUrl;
    }


    public static class dic implements Serializable {
        public String id;
        public String name;
        public String value;
    }

    public class SignsList implements Serializable {
        public String loginName;
        public String saveTime;
        public String signId;
        public String signPic;
        public String signPicUrl;
        public String signType;
        public String userId;
        public String userName;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isNameVisible() {
        return NameVisible;
    }

    public void setNameVisible(int nameVisible) {
        NameVisible = nameVisible == 1;
    }

    public String getSplitString() {
        return SplitString;
    }

    public void setSplitString(String splitString) {
        SplitString = splitString;
    }

    public boolean isNameRN() {
        return NameRN;
    }

    public void setNameRN(boolean nameRN) {
        NameRN = nameRN;
    }

    public String getValue() {
        if(TextUtils.isEmpty(Value)){
            Value = "";
        }
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getBeforeValueString() {
        return BeforeValueString;
    }

    public void setBeforeValueString(String beforeValueString) {
        BeforeValueString = beforeValueString;
    }

    public String getEndValueString() {
        return EndValueString;
    }

    public void setEndValueString(String endValueString) {
        EndValueString = endValueString;
    }

    public int getPercent() {
        return Percent;
    }

    public void setPercent(int percent) {
        Percent = percent;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        DisplayOrder = displayOrder;
    }

    public String getAlign() {
        return Align;
    }

    public void setAlign(String align) {
        Align = align;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getInput() {
        if(TextUtils.isEmpty(Input)){
            Input = "0";
        }
        return Input;
    }

    public void setInput(String input) {
        Input = input;
    }

    public boolean isMustInput() {
        return MustInput;
    }

    public void setMustInput(boolean mustInput) {
        MustInput = mustInput;
    }

    public String getFormKey() {
        return FormKey;
    }

    public void setFormKey(String formKey) {
        FormKey = formKey;
    }

    public int isSplitWithLine() {
        return IsSplitWithLine;
    }

    public void setIsSplitWithLine(int isSplitWithLine) {
        IsSplitWithLine = isSplitWithLine;
    }

    public int getNameFontColor() {
        return NameFontColor;
    }

    public void setNameFontColor(int nameFontColor) {
        NameFontColor = nameFontColor;
    }

    public int getValueFontColor() {
        return ValueFontColor;
    }

    public void setValueFontColor(int valueFontColor) {
        ValueFontColor = valueFontColor;
    }

    public int getMaxLength() {
        return MaxLength;
    }

    public void setMaxLength(int maxLength) {
        MaxLength = maxLength;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public int getEditable() {
        return editable;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }

    public boolean isExt() {
        return isExt;
    }

    public void setIsExt(int isExt) {
        this.isExt = isExt == 1;
//        this.isExt = isExt;
    }

    public String getDependFieldKey() {
        return dependFieldKey;
    }

    public void setDependFieldKey(String dependFieldKey) {
        this.dependFieldKey = dependFieldKey;
    }

    public int getBackColor() {
        return BackColor;
    }

    public void setBackColor(int backColor) {
        BackColor = backColor;
    }

    public TabEvent getAjaxEvent() {
        return ajaxEvent;
    }

    public void setAjaxEvent(TabEvent ajaxEvent) {
        this.ajaxEvent = ajaxEvent;
    }

    public List<opintion> getOpintions() {
        return opintions;
    }

    public void setOpintions(List<opintion> opintions) {
        this.opintions = opintions;
    }

    public List<dic> getDics() {
        return Dics;
    }

    public void setDics(List<dic> dics) {
        Dics = dics;
    }

    public List<FormExtensionFiles> getFiledImages() {
        return filedImages;
    }

    public void setFiledImages(List<FormExtensionFiles> filedImages) {
        this.filedImages = filedImages;
    }

    public List<FormExtensionFiles> getFiledAudios() {
        return filedAudios;
    }

    public void setFiledAudios(List<FormExtensionFiles> filedAudios) {
        this.filedAudios = filedAudios;
    }

    public List<FormExtensionFiles> getFiledVideos() {
        return filedVideos;
    }

    public void setFiledVideos(List<FormExtensionFiles> filedVideos) {
        this.filedVideos = filedVideos;
    }
}
