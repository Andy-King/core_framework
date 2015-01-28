package org.jee.framework.web.utils;

import java.text.SimpleDateFormat;

public class Constants {
    
    /**
     * 目录命名格式: yyyyMM
     */
    public static final SimpleDateFormat FORMATTER_DIR = new SimpleDateFormat("yyyyMM");

    /**
     * 文件命名格式: yyyyMMddHHmmssSSS
     */
    public static final SimpleDateFormat FORMATTER_FILE = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    /**
     * 文件上传路径
     */
    public static final String PATH_UPLOAD_FILE = "/uploadfiles/";

    /**
     * 用于 HTTP session 中保存的验证码
     * @org.jee.framework.web.utils.ImageCaptchaServlet
     */
    public final static String HTTP_SESSION_CAPTCHA_CODE = "captcha_code";
}