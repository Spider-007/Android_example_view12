package com.htmitech.htcommonformplugin.entity;

import java.io.Serializable;

/**
 * Created by Think on 2017/4/25.
 */

public class Message implements Serializable{

    public String StatusCode;
    public String StatusMessage;
    public String ElementName;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public String getElementName() {
        return ElementName;
    }

    public void setElementName(String elementName) {
        ElementName = elementName;
    }
}
