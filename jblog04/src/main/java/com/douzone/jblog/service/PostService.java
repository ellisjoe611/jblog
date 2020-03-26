package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVO;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	// 하나의 포스트 추가
	// [title, contents, category_no] >>> process count
	public Boolean addPost(PostVO vo) {
		if(vo.getTitle() == null || "".equals(vo.getTitle())) {
			return false;
		}
		if(vo.getContents() == null || "".equals(vo.getContents())) {
			return false;
		}
		if(vo.getCategory_no() == null) {
			return false;
		}

		return postRepository.insert(vo);
	}

	// 포스트 목록 list 조회
	// 하나의 포스트 내용 view 밑에 목록 전체를 출력하는 용도로 쓰인다.
	// [category_no] >>> list [no, title, reg_date(년/월/일 까지만), category_no]
	public List<PostVO> getPostsByCategory(Long category_no){
		PostVO vo = new PostVO();
		vo.setCategory_no(category_no);
		
		return postRepository.findAll(vo);
	}
	
	// 포스트 상세내용 조회
	public PostVO getPost(Long category_no, Long post_no) {
		PostVO vo = new PostVO();
		vo.setNo(post_no);
		vo.setCategory_no(category_no);
		
		return postRepository.find(vo);
	}

}
