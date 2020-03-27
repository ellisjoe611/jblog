package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVO;

@Repository
public class CategoryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 카테고리를 추가
	// [name, description, user_id] >>> process count
	public Boolean insert(CategoryVO vo) {
		return sqlSession.insert("category.insert", vo) == 1;
	}
	
	// 카테고리 삭제
	// 주의 : 관련 포스트들을 먼저 삭제한 후에 실행할것!, '미분류'로 되어있는 항목은 삭제금지 및 false 리턴
	// [no, user_id] >>> process count
	public Boolean delete(CategoryVO vo) {
		Long initialCategoryId = sqlSession.selectOne("category.findInitialCategoryId", vo);
		if(initialCategoryId == vo.getNo()) {
			return false;
		}
		return sqlSession.delete("category.delete", vo) == 1;
	}
	
	// 카테고리 목록 list 조회
	// [user_id] >>> list [no, name, posted(post와 연동), description, reg_date, user_id]
	public List<CategoryVO> findAll(CategoryVO vo) {
		List<CategoryVO> result = sqlSession.selectList("category.findAll", vo);
		return result;
	}
	
	// 카테고리 no & name 목록 list 조회
	// 포스트에서 추가 시 카테고리 종류를 선택하는 용도로 쓰임 (스크롤 메뉴)
	// [user_id] >>> list [no, name]
	public List<CategoryVO> findScrollList(CategoryVO vo) {
		List<CategoryVO> result = sqlSession.selectList("category.findListByNoAndName", vo);
		return result;
	}
	
	// 카테고리의 CategoryVO.no를 통해 현재 로그인한 사용자의 UserVO.id와 Category.user_id와 일치하는지 확인 
	// [no] >>> [user_id]
	public CategoryVO findUserIdByCategoryNo(CategoryVO vo) {
		return (CategoryVO) sqlSession.selectOne("category.findUserIdByCategoryNo", vo);
	}
	
}
