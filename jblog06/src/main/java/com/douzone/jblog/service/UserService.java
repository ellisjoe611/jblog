package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	// 회원가입 프로세스
	// User -> Blog -> Category 순으로 등록한다.
	// 카테고리를 최초 추가 시 name = '미분류', des 으로 한 체로 추가한다 (이 때에는 반드시 user, blog 추가 후 실행)
	public Boolean join(UserVO userVo) {		
		BlogVO blogVo = new BlogVO();
		blogVo.setUser_id(userVo.getId());
		blogVo.setTitle(userVo.getId() + " 님의 블로그");
		blogVo.setLogo("default.jpg");
		
		CategoryVO categoryVo = new CategoryVO();
		categoryVo.setName("미분류");
		categoryVo.setDescription("지정되지 않은 카테고리");
		categoryVo.setUser_id(userVo.getId());
		
		if(userRepository.insert(userVo) == false) {
			return false;
		}
		if(blogRepository.insert(blogVo) == false) {
			return false;
		}
		return categoryRepository.insert(categoryVo);
		
	}
	
	public Boolean inputCheck(UserVO userVo) {
		if(userVo.getName() == null || "".equals(userVo.getName())) {
			return false;
		}
		if(userVo.getId() == null || "".equals(userVo.getId())) {
			return false;
		}
		if(userVo.getPw() == null || "".equals(userVo.getPw())) {
			return false;
		}
		return true;
	}
	
	// 로그인용. [id, name]이 담긴 UserVO 객체를 불러온다.
	public UserVO login(UserVO vo) {
		return userRepository.findUser(vo);
	}

	// 회원 가입시 기존에 가입된 이메일의 존재 여부를 확인
	// json 형태의 결과 데이터 전송용
	public boolean existUser(String id) {
		return userRepository.findUserId(id) == null;
	}

}
