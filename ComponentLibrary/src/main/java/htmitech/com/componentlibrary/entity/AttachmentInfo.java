package htmitech.com.componentlibrary.entity;

import java.io.File;
import java.io.Serializable;

public class AttachmentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String AttachmentDateTime;

    private String AttachmentID;

    private String AttachmentTitle;

    private String AttachmentType;

    private double AttachmentSize;

    private boolean encrypt;

    private boolean isZip;
    // tg  add:是否已读
    private boolean isDownload;
    // tg add:存在本地的名字和URI地址
    private File URI;

    /**
     * 是否处于压缩状态：压缩状态不能打开需要解压
     **/

    public String getAttachmentID() {
        return AttachmentID;
    }

    public String getAttachmentTitle() {
        return AttachmentTitle;
    }

    public String getAttachmentType() {
        return AttachmentType.toLowerCase();
    }

    public double getAttachmentSize() {
        return AttachmentSize;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setIsZip(boolean isZip) {
        this.isZip = isZip;
    }

    public boolean getIsZip() {
        return isZip;
    }

    public File getURI() {
        return URI;
    }

    public void setURI(File uRI) {
        URI = uRI;
    }

    public void setAttachmentID(String attachmentID) {
        this.AttachmentID = attachmentID;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        this.AttachmentTitle = attachmentTitle;
    }

    public void setAttachmentType(String attachmentType) {
        this.AttachmentType = attachmentType;
    }

    public void setAttachmentSize(double attachmentSize) {
        this.AttachmentSize = attachmentSize;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public void setIsDownload(boolean isRead) {
        this.isDownload = isRead;
        isZip = true; // 刚下载的一定是处于压缩状态的
    }

    public boolean getIsDownload() {
        return isDownload;
    }

    public String getAttachmentDateTime() {
        return AttachmentDateTime;
    }

    public void setAttachmentDateTime(String attachmentDateTime) {
        AttachmentDateTime = attachmentDateTime;
    }

    public void setZip(boolean isZip) {
        this.isZip = isZip;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

}
