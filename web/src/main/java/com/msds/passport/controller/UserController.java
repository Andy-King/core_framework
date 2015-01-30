package com.msds.passport.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jee.framework.core.utils.DigestUtils;
import org.jee.framework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.msds.passport.entity.User;
import com.msds.passport.service.UserService;

/**
 * http://127.0.0.1:8080/user/list.htm
 * @author AK
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	private Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="userService")
	private UserService userService;

    @RequestMapping(value = "list.htm", method = RequestMethod.GET)
    public String list(Model model) {
    	User user = new User();
		user.setUserName("test");
		user.setPassword(DigestUtils.md5("123456"));
		user.setEmail("test@163.com");
        model.addAttribute("users", user);
        // /framework-web/src/main/resources/spring/mvc/spring-mvc-servlet.xml
        // /WEB-INF/views/jsp/
        return "user/list";
    }

    @RequestMapping(value = "create.htm", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }
    
    @RequestMapping(value="test.htm",method =RequestMethod.GET)
    public String test(Model m){
    	m.addAttribute("sss","ssss");
    	return "user/list";
    }
    
    /**
     * http://127.0.0.1/user/test0.htm
     * @param m
     * @return
     */
    @RequestMapping(value="test0.htm",method =RequestMethod.GET)
    public @ResponseBody User test0(Model m){
    	User user = new User();
		user.setUserName("test");
		user.setPassword(DigestUtils.md5("123456"));
		user.setEmail("test@163.com");
		LOG.info("test0.htm, --> user:{}", user);
    	return user;
    }
    
    /**
     * http://127.0.0.1/user/test1.htm?callback=test
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="test1.htm",method =RequestMethod.GET)
    public ResponseEntity<String> test1(HttpServletRequest request) throws IOException{
    	User user = new User();
		user.setUserName("test");
		user.setPassword(DigestUtils.md5("123456"));
		user.setEmail("test@163.com");
		LOG.info("test1.htm, --> user:{}", user);
		//String responseData = JSON.json(user);
		//HttpHeaders headers = new HttpHeaders();
		//headers.add("Content-Type", "application/json; charset=utf-8");
		//return new ResponseEntity<String>(responseData, headers, HttpStatus.OK);
		
		return getResponseEntity(request, user);
    }
    
    
    public ResponseEntity<String> getResponseEntity(HttpServletRequest request, Object data) throws IOException {
		String cors = null == request ? null : request.getParameter("callback");
		String responseData = toJsonByCallback(data, cors);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<String>(responseData, headers, HttpStatus.OK);
	}
    
	/**
	 * 根据callback参数来决定生成不同的Json数据(AJAX跨域请求JSON数据)
	 * 
	 * @param callback
	 *            如果有callback参数值，则表示跨域请求JSON，需要生成JSONP格式数据；否则为传统JSON格式数据。
	 * @param object
	 *            需要进行转换的对象。
	 * @return json字符串。
	 * @throws IOException 
	 */
	public String toJsonByCallback(Object object, String callback) throws IOException {
		final String json = JSON.json(object);
		if (StringUtils.isBlank(callback)) { // 非跨域请求
			return json;
		} else {
			final StringBuilder sb = new StringBuilder(json.length() + callback.length() + 2);
			sb.append(callback);
			sb.append("(");
			sb.append(json);
			sb.append(")");
			return sb.toString();
		}
	}
}