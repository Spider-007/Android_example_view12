package com.htmitech.emportal.common;

import java.util.HashMap;

import com.htmitech.emportal.R;

public class CommonFileType {

    public static final String[][] MIME_MapTable = {
            //{后缀名，MIME类型} 
            {"3gp", "video/3gpp"},
            {"apk", "application/vnd.android.package-archive"},
            {"asf", "video/x-ms-asf"},
            {"avi", "video/x-msvideo"},
            {"bin", "application/octet-stream"},
            {"bmp", "image/bmp"},
            {"c", "text/plain"},
            {"class", "application/octet-stream"},
            {"conf", "text/plain"},
            {"cpp", "text/plain"},
            {"doc", "application/msword"},
            {"docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"xls", "application/vnd.ms-excel"},
            {"xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"exe", "application/octet-stream"},
            {"gif", "image/gif"},
            {"gtar", "application/x-gtar"},
            {"gz", "application/x-gzip"},
            {"h", "text/plain"},
            {"htm", "text/html"},
            {"html", "text/html"},
            {"jar", "application/java-archive"},
            {"java", "text/plain"},
            {"jpeg", "image/jpeg"},
            {"jpg", "image/jpeg"},
            {"js", "application/x-javascript"},
            {"log", "text/plain"},
            {"m3u", "audio/x-mpegurl"},
            {"m4a", "audio/mp4a-latm"},
            {"m4b", "audio/mp4a-latm"},
            {"m4p", "audio/mp4a-latm"},
            {"m4u", "video/vnd.mpegurl"},
            {"m4v", "video/x-m4v"},
            {"mov", "video/quicktime"},
            {"mp2", "audio/x-mpeg"},
            {"mp3", "audio/x-mpeg"},
            {"mp4", "video/mp4"},
            {"mpc", "application/vnd.mpohun.certificate"},
            {"mpe", "video/mpeg"},
            {"mpeg", "video/mpeg"},
            {"mpg", "video/mpeg"},
            {"mpg4", "video/mp4"},
            {"mpga", "audio/mpeg"},
            {"msg", "application/vnd.ms-outlook"},
            {"ogg", "audio/ogg"},
            {"pdf", "application/pdf"},
            {"png", "image/png"},
            {"pps", "application/vnd.ms-powerpoint"},
            {"ppt", "application/vnd.ms-powerpoint"},
            {"pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {"prop", "text/plain"},
            {"rc", "text/plain"},
            {"rmvb", "audio/x-pn-realaudio"},
            {"rtf", "application/rtf"},
            {"sh", "text/plain"},
            {"tar", "application/x-tar"},
            {"tgz", "application/x-compressed"},
            {"txt", "text/plain"},
            {"wav", "audio/x-wav"},
            {"wma", "audio/x-ms-wma"},
            {"wmv", "audio/x-ms-wmv"},
            {"wps", "application/vnd.ms-works"},
            {"xml", "text/plain"},
            {"z", "application/x-compress"},
            {"zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    private static HashMap<String, Integer> MIME_icon_MapTable = new HashMap<String, Integer>();

    public static HashMap<String, Integer> MIME_icon_MapTable_Instance() {
        if (MIME_icon_MapTable.size() == 0) {
            initMIME_icon_MapTable();
        }
        return MIME_icon_MapTable;
    }

    private static void initMIME_icon_MapTable() {
        MIME_icon_MapTable.put("3gp", R.drawable.icon_video);
        MIME_icon_MapTable.put("avi", R.drawable.icon_video);
        MIME_icon_MapTable.put("m4u", R.drawable.icon_video);
        MIME_icon_MapTable.put("m4v", R.drawable.icon_video);
        MIME_icon_MapTable.put("mov", R.drawable.icon_video);
        MIME_icon_MapTable.put("mp4", R.drawable.icon_video);

        MIME_icon_MapTable.put("m3u", R.drawable.icon_audio);
        MIME_icon_MapTable.put("m4a", R.drawable.icon_audio);
        MIME_icon_MapTable.put("m4b", R.drawable.icon_audio);
        MIME_icon_MapTable.put("m4p", R.drawable.icon_audio);
        MIME_icon_MapTable.put("mp2", R.drawable.icon_audio);
        MIME_icon_MapTable.put("mp3", R.drawable.icon_audio);
        MIME_icon_MapTable.put("mpga", R.drawable.icon_audio);
        MIME_icon_MapTable.put("ogg", R.drawable.icon_audio);
        MIME_icon_MapTable.put("rmvb", R.drawable.icon_audio);
        MIME_icon_MapTable.put("wav", R.drawable.icon_audio);
        MIME_icon_MapTable.put("wma", R.drawable.icon_audio);
        MIME_icon_MapTable.put("wmv", R.drawable.icon_audio);

        // MIME_icon_MapTable.put("apk", R.drawable.ic_apk);

        MIME_icon_MapTable.put("bmp", R.drawable.icon_images);
        MIME_icon_MapTable.put("gif", R.drawable.icon_images);
        MIME_icon_MapTable.put("jpeg", R.drawable.icon_images);
        MIME_icon_MapTable.put("jpg", R.drawable.icon_images);
        MIME_icon_MapTable.put("gif", R.drawable.icon_images);
        MIME_icon_MapTable.put("png", R.drawable.icon_images);

        MIME_icon_MapTable.put("BMP", R.drawable.icon_images);
        MIME_icon_MapTable.put("GIF", R.drawable.icon_images);
        MIME_icon_MapTable.put("JPEG", R.drawable.icon_images);
        MIME_icon_MapTable.put("JPG", R.drawable.icon_images);
        MIME_icon_MapTable.put("GIF", R.drawable.icon_images);
        MIME_icon_MapTable.put("PNG", R.drawable.icon_images);

        MIME_icon_MapTable.put("svg", R.drawable.icon_images);
        MIME_icon_MapTable.put("tif", R.drawable.icon_images);
        MIME_icon_MapTable.put("ico", R.drawable.icon_images);

        MIME_icon_MapTable.put("c", R.drawable.icon_txt);
        MIME_icon_MapTable.put("class", R.drawable.icon_txt);
        MIME_icon_MapTable.put("conf", R.drawable.icon_txt);
        MIME_icon_MapTable.put("cpp", R.drawable.icon_txt);
        MIME_icon_MapTable.put("h", R.drawable.icon_txt);
        MIME_icon_MapTable.put("java", R.drawable.icon_txt);
        MIME_icon_MapTable.put("txt", R.drawable.icon_txt);

        MIME_icon_MapTable.put("wps", R.drawable.icon_word);
        MIME_icon_MapTable.put("doc", R.drawable.icon_word);
        MIME_icon_MapTable.put("docx", R.drawable.icon_docx);
        MIME_icon_MapTable.put("xlsx", R.drawable.icon_excle);
        MIME_icon_MapTable.put("xls", R.drawable.icon_excle);
        MIME_icon_MapTable.put("pps", R.drawable.icon_excle);
        MIME_icon_MapTable.put("ppt", R.drawable.icon_ppt);
        MIME_icon_MapTable.put("pptx", R.drawable.icon_ppt);

        // MIME_icon_MapTable.put("tgz", R.drawable.ic_rar);
        // MIME_icon_MapTable.put("gtar", R.drawable.ic_rar);
        // MIME_icon_MapTable.put("rar", R.drawable.ic_rar);
        // MIME_icon_MapTable.put("gz", R.drawable.ic_rar);
        // MIME_icon_MapTable.put("zip", R.drawable.ic_rar);

        MIME_icon_MapTable.put("rtf", R.drawable.icon_dat);
        MIME_icon_MapTable.put("xml", R.drawable.icon_dat);
        MIME_icon_MapTable.put("htm", R.drawable.icon_dat);
        MIME_icon_MapTable.put("html", R.drawable.icon_dat);
        MIME_icon_MapTable.put("exe", R.drawable.icon_dat);
        MIME_icon_MapTable.put("exe", R.drawable.icon_dat);

        MIME_icon_MapTable.put("pdf", R.drawable.icon_pdf);
        MIME_icon_MapTable.put("exe", R.drawable.icon_dat);
        MIME_icon_MapTable.put("exe", R.drawable.icon_dat);
        MIME_icon_MapTable.put("exe", R.drawable.icon_dat);
        MIME_icon_MapTable.put("exe", R.drawable.icon_dat);

    }
}
