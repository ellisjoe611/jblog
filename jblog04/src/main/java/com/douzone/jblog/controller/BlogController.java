package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.PostVO;
import com.douzone.jblog.vo.UserVO;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping(value = "/{id:(?!assets|user|images|api).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping(value = { "", "/{categoty}", "/{categoty}/{post}" }, method = RequestMethod.GET)
	public String displayAll(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId,
			@PathVariable Optional<Long> categoty, @PathVariable Optional<Long> post, Model model) {

		// 1. 블로그 페이지의 정보 블러오기 ( [user_id] >>> [user_id, title, logo] )
		// ++ 해당 블로그의 수정 권한까지 넘기기
		BlogVO blogVo = blogService.getBlogInfo(blogOwnerId);
		if (blogVo == null) {
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		if (authUser != null && authUser.getId().equals(blogVo.getUser_id())) {
			model.addAttribute("managable", true);
		}

		Long categoryNo, postNo;

		// 2. user_id가 가지고 있는 category 목록을 블러오기 ( [user_id] >>> list [no, name] )
		// 만약 categoryNo가 없으면 '미분류'에 해당되는 category_no를 찾아서 넘긴다.
		List<CategoryVO> categoryList = categoryService.getCategoryScroll(blogOwnerId);
		if (categoty.isPresent()) {
			categoryNo = categoty.get();
		} else {
			categoryNo = categoryList.get(0).getNo();
		}
		model.addAttribute("categoryList", categoryList);

		// 3. user_id가 가지고 있는 포스트 목록 출력 ( [category_no] >>> list [no, title,
		// reg_date(년/월/일 까지만), category_no] )
		// 만약 postNo가 없으면...?
		List<PostVO> postList = postService.getPostsByCategory(categoryNo);

		// 4. 마지막으로 postNo를 가지고 해당 post 출력하기
		// 포스트가 하나 이상 있다는 전제 하에 실행하기
		if (postList != null && postList.size() > 0) {
			if (post.isPresent()) {
				postNo = post.get();
			} else {
				postNo = postList.get(0).getNo();
			}
			model.addAttribute("postList", postList);

			PostVO selectedPostVo = postService.getPost(categoryNo, postNo);
			model.addAttribute("selectedPostVo", selectedPostVo);
		}

		return "blog/blog-main";
	}

	// blog-admin-basic.jsp 접속
	// @AuthUser를 통해 접근 제어하기
	@Auth
	@RequestMapping(value = { "/admin", "/admin/basic" }, method = RequestMethod.GET)
	public String adminBasic(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId, Model model) {

		if (blogOwnerId.equals(authUser.getId()) != true) {
			return "redirect:/";
		}

		// authUser 내의 [id]를 통해 블로그 정보 [user_id, title, logo]를 블러온다.
		// [user_id] >>> [user_id, title, logo]
		BlogVO blogVo = blogService.getBlogInfo(authUser.getId());
		if (blogVo == null) {
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		if (authUser.getId().equals(blogVo.getUser_id())) {
			model.addAttribute("managable", true);
		}

		return "blog/blog-admin-basic";
	}

	// 블로그 업데이트 ( [title, logo(??), user_id] >>> true/false )
	@Auth
	@RequestMapping(value = { "/admin/basic/update" }, method = RequestMethod.POST)
	public String adminBasicUpdate(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId,
			@RequestParam(value = "title", required = true, defaultValue = "") String newTitle,
			@RequestParam(value = "logo-file") MultipartFile multipartFile, Model model) {

		if (blogOwnerId.equals(authUser.getId()) != true) {
			return "redirect:/";
		}

		BlogVO blogVo = new BlogVO();
		if (newTitle == null || "".equals(newTitle)) {
			blogVo.setTitle(authUser.getId() + " 님의 블로그");
		} else {
			blogVo.setTitle(newTitle);
		}
		blogVo.setLogo(fileUploadService.restore(multipartFile));
		blogVo.setUser_id(authUser.getId());

		blogService.changeBlog(blogVo);

		return "redirect:/" + blogOwnerId + "/admin";
	}

	// 카테고리 목록 페이지
	@Auth
	@RequestMapping(value = { "/admin/category" }, method = RequestMethod.GET)
	public String adminCategory(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId, Model model) {

		if (blogOwnerId.equals(authUser.getId()) != true) {
			return "redirect:/";
		}

		// authUser 내의 [id]를 통해 블로그 정보 [user_id, title, logo]를 블러온다.
		// [user_id] >>> [user_id, title, logo]
		BlogVO blogVo = blogService.getBlogInfo(authUser.getId());
		if (blogVo == null) {
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		if (authUser.getId().equals(blogVo.getUser_id())) {
			model.addAttribute("managable", true);
		}

		return "blog/blog-admin-category";
	}

	

	// 포스트 작성 페이지
	// [title, contents, category_no] >>> process count
	@Auth
	@RequestMapping(value = { "/admin/write" }, method = RequestMethod.GET)
	public String adminPost(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId, Model model) {

		if (blogOwnerId.equals(authUser.getId()) != true) {
			return "redirect:/";
		}

		// authUser 내의 [id]를 통해 블로그 정보 [user_id, title, logo]를 블러온다.
		// [user_id] >>> [user_id, title, logo]
		BlogVO blogVo = blogService.getBlogInfo(authUser.getId());
		if (blogVo == null) {
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		if (authUser.getId().equals(blogVo.getUser_id())) {
			model.addAttribute("managable", true);
		}

		// 카테고리 스크롤를 받아서 넘긴다.
		model.addAttribute("categoryScrollList",
				(List<CategoryVO>) categoryService.getCategoryScroll(authUser.getId()));

		return "blog/blog-admin-write";
	}

	// 포스트 저장 프로세스
	@Auth
	@RequestMapping(value = { "/admin/write" }, method = RequestMethod.POST)
	public String adminPostAdd(@AuthUser UserVO authUser, @PathVariable("id") String blogOwnerId, PostVO vo,
			Model model) {

		if (blogOwnerId.equals(authUser.getId()) != true) {
			return "redirect:/";
		}

		if (postService.addPost(vo) == false) {
			model.addAttribute("previousVo", vo);
			return "redirect:/" + blogOwnerId + "/admin/write";
		} else {
			return "redirect:/" + blogOwnerId + "/" + vo.getCategory_no() + "/" + vo.getNo();
		}

	}
}
