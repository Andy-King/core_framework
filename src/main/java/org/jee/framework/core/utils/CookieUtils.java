package org.jee.framework.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 单个cookie在客户端的限制是3K
 * 
 * key            cookie中的关键字     (必填项)
 * values         关键字所对应的内容    (必填项)
 * maxAge         生存周期             (必填项)
 *                如果设置为负值的话，则为浏览器进程中的Cookie(内存中保存)，关闭浏览器就失效。
 *                        
 * domain         域名                (必填项)
 *                例: www.SinoFloat.cn能够访问bbs.SinoFloat.cn, 
 *                    则domain 设置为"SinoFloat.cn", path设置为"/"
 *                另: 不能把cookies域属性设置成与它的服务器的所在域不同的值。
 *                        
 * path           cookie存放的路径
 *                如: path值为: "/test"; damain: "SinoFloat.cn"
 *					  则cookie中存的path是 SinoFloat.cn/test1，
 *					  那么 SinoFloat.cn/test1目录下里及子目录的页面都可以访问这个cookie
 *					  那么 SinoFloat.cn/test2的项目就不能使用test1项目的cookie。
 *					  若要test1 和 test2 共享cookie， 则path设置为: "/"
 * request        HttpServletRequest
 * response       HttpServletResponse
 */

public abstract class CookieUtils {
	/**
	 * 在客户端添加一个cookie
	 */
	public static void addCookie(String key, String values, int maxAge,String domain,
				String path, HttpServletResponse response){
		Cookie cookie = new Cookie(key,values);
		cookie.setMaxAge(maxAge);
		if (StringUtils.isNotEmpty(domain)) {
			cookie.setDomain(domain);
		}
		
		// 默认将path指向到域名的根目录下 
		if (path == null || "".equals(path)) {
			cookie.setPath("/");
		} else{
			cookie.setPath(path);
		}
		
		response.addCookie(cookie);
	}
	
	/**
	 * 获取客户端的cookie
	 */
	public static String getCookie (String key, HttpServletRequest request) {
		Cookie cookies[] = request.getCookies(); 

		if (cookies != null && cookies.length != 0){
			for (Cookie tmpCookie : cookies) {
				if ( key.equals(tmpCookie.getName()) ) {
					return tmpCookie.getValue();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 删除客户端的cookie
	 */
	public static void delCookie (String key, String domain, String path,
						HttpServletRequest request,HttpServletResponse response) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		
		// 默认将path指向到域名的根目录下 
		if (path == null || "".equals(path)) {
			cookie.setPath("/");
		} else{
			cookie.setPath(path);
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 更新 cookie
	 */
	public static void addOrUpdateCookie(String key, String values, int maxAge,String domain, String path,
					HttpServletRequest request,HttpServletResponse response){
		if ( getCookie(key, request) != null ) {
			delCookie(key, domain, path, request,response);
			addCookie(key, values, maxAge, domain, path, response);
		} else {
			addCookie(key, values, maxAge, domain, path, response);
		}
	}
}