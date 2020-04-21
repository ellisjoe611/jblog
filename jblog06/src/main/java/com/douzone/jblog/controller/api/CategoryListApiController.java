package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.UserVO;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller("CategoryListApiController")
@RequestMapping("/api/category")
public class CategoryListApiController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ResponseBody
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public JsonResult list(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId) {
		List<CategoryVO> categoryList = categoryService.getCategoryList(blogOwnerId);
		
		return JsonResult.success(categoryList);
	}
	
	@Auth
	@ResponseBody
	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
	public JsonResult add(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId, @RequestBody CategoryVO vo) {
		if(blogOwnerId.equals(authUser.getId()) == false) {
			return JsonResult.fail("사용자 불일치...");
		}
		
		Boolean result = categoryService.addCategory(vo.getName(), vo.getDescription(), authUser.getId());
		if(result == false) {
			return JsonResult.fail("데이터 추가 실패..." + vo.toString());			
		}
		
		List<CategoryVO> inserted = categoryService.getLastInsertedCategoryList(blogOwnerId);
		return JsonResult.success(inserted);
	}
	
	@Auth
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public JsonResult delete(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId, @RequestParam(value = "no", required = true, defaultValue = "0") Long no) {
		if(blogOwnerId.equals(authUser.getId()) == false) {
			return JsonResult.fail("사용자 불일치...");
		}
		
		Boolean result = categoryService.removeCategory(no, authUser.getId());
		if(result == false) {
			return JsonResult.fail("데이터 삭제 실패...blogOwnerId: " + blogOwnerId + " / no: " + no);			
		}
		
		return JsonResult.success(result);
	}	

}
