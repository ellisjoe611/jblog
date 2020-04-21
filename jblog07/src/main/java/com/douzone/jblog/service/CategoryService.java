package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.PostVO;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	// 추가적인 카테고리를 추가하기
	// [name, description, user_id] >>> process count
	public Boolean addCategory(String name, String description, String user_id) {
		CategoryVO vo = new CategoryVO();
		vo.setName(name);
		vo.setDescription(description);
		vo.setUser_id(user_id);
		
		return categoryRepository.insert(vo);
	}
	
	// 카테고리를 삭제하기
	// 같은 카테고리의 post들 제거 -> 해당 카테고리 제거
	// [no, user_id] >>> process count
	public Boolean removeCategory(Long no, String user_id) {
		PostVO postVo = new PostVO();
		postVo.setCategory_no(no);
		postVo.setUser_id(user_id);
		
		CategoryVO categoryVo = new CategoryVO();
		categoryVo.setNo(no);
		categoryVo.setUser_id(user_id);
		
		CategoryVO temp = categoryRepository.findUserIdByCategoryNo(categoryVo);
		if(temp == null || user_id.equals(temp.getUser_id()) != true) {
			return false;
		}
		if(postRepository.deleteByCategoryNo(postVo) == false) {
			return false;
		}
		return categoryRepository.delete(categoryVo);
	}
	
	// 카테고리 목록 list 조회
	// [user_id] >>> list [no, name, posted(post와 연동), description, reg_date, user_id]	
	public List<CategoryVO> getCategoryList(String user_id) {
		CategoryVO vo = new CategoryVO();
		vo.setUser_id(user_id);
		
		return categoryRepository.findAll(vo);
	}
	
	// 카테고리 no & name 목록 list 조회
	// 포스트에서 추가 시 카테고리 종류를 선택하는 용도로 쓰임 (스크롤 메뉴)
	// [user_id] >>> list [no, name]
	public List<CategoryVO> getCategoryScroll(String user_id){
		CategoryVO vo = new CategoryVO();
		vo.setUser_id(user_id);
		
		return categoryRepository.findScrollList(vo);
	}

	public List<CategoryVO> getLastInsertedCategoryList(String blogOwnerId) {
		return categoryRepository.findLastUpdated(blogOwnerId);
	}
	
}
