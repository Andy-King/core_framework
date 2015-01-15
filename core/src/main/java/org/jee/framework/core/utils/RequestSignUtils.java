package org.jee.framework.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Request Sign utils
 * @author Andy King
 *
 */
public final class RequestSignUtils {
    
    /** SALT */
    private final static String SALT = "&_=$1$8$8$8^%*()86$87#88##90%^";
    
    
    /**
     * default equals params.get("sign")
     * @param params
     * @return
     */
    public boolean verify(Map<String, String> params){
        return verify(params, params.get("sign"));
    }
    
    public boolean verify(Map<String, String> params, String sign){
    	return null != sign && getMySign(params).equals(sign) ? Boolean.TRUE : Boolean.FALSE;
    }

    public String getMySign(Map<String, String> params){
        return getMySign(params, SALT);
        
    }
    
    public String getMySign(Map<String, String> params, String salt){
    	final Map<String, String> map = paraFilter(params);
    	return buildMysign(map, salt);
    	
    }

    /**
     * 生成签名结果
     * @param sArray 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildMysign(Map<String, String> sArray, String salt) {
        
    	//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	//String prestr = createLinkString(sArray);
        //把拼接后的字符串再与安全校验码直接连接起来
        //prestr = prestr + salt;
        final String mysign = EncryptUtils.md5(createLinkString(sArray).append(salt).toString(), "UTF-8");
        return mysign;
    }
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static StringBuilder createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                sb.append(key).append("=").append(value);
            } else {
                sb.append(key).append("=").append(value).append("&");
            }
        }

        return sb;
    }

    /**
    * 获得请求参数Map<String,String>
    */
    @SuppressWarnings("unchecked")
	public static Map<String, String> getRequestParam(HttpServletRequest request){
        //获取request过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String, ?> requestParams = request.getParameterMap();
        for (Map.Entry<String, ?> entry : requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = (String[])entry.getValue();
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }
        
        return params;
    }
}
