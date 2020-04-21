package com.douzone.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.UserService;

@Controller("UserApiController")
@RequestMapping("/api/user")
public class UserApiController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/checkid", method = RequestMethod.GET)
	public JsonResult checkEmail(@RequestParam(value = "id", required = true, defaultValue = "") String id) {
		boolean exist = userService.existUser(id);

		return JsonResult.success(exist);
	}
}
