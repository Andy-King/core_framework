package com.msds.passport.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.msds.passport.entity.User;
import com.msds.passport.service.UserService;

/**
 * http://127.0.0.1:8080/user/list.htm
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource(name="userService")
	private UserService userService;

    @RequestMapping(value = "list.htm", method = RequestMethod.GET)
    public String list(Model model) {
    	User user = userService.getEntity("userName", "test02");
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

}