package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVO;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	// authUser 내의 [id]를 통해 블로그 정보 [user_id, title, logo]를 블러온다.
	// [user_id] >>> [user_id, title, logo]
	public BlogVO getBlogInfo(String id) {
		BlogVO vo = new BlogVO();
		vo.setUser_id(id);
		
		return blogRepository.find(vo);
	}
	
	// 블로그 정보 수정
	// Controller 단계에서 BlogVO 객체로 정리해서 결과여부를 리턴한다. (title 명, logo 파일이름)
	public Boolean changeBlog(BlogVO vo) {
		return blogRepository.update(vo);
	}
	
}
