package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "", "/login" }, method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = { "/auth" }, method = RequestMethod.POST)
	public void auth() {

	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public void logout() {

	}
	
	@RequestMapping(value = { "/join" }, method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value = { "/join" }, method = RequestMethod.POST)
	public String join(UserVO userVo, Model model) {
		if(userService.inputCheck(userVo) == false || userService.join(userVo) == false) {
			model.addAttribute("previousVo", userVo);
			return "user/join";
		
		} else {
			return "redirect:/user/joinsuccess";
		}
	}
	
	@RequestMapping(value = { "/joinsuccess" })
	public String joinSuccess() {
		return "user/joinsuccess";
	}

}
