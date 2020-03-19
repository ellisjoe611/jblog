package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVO;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;
	
	// 포스트 추가
	// [title, contents, category_no] >>> process count
	public Boolean insert(PostVO vo) {
		return sqlSession.insert("post.insert", vo) == 1;
	}
	
	// 한 개의 포스트 삭제
	// [no] >>> process count
	public Boolean delete(PostVO vo) {
		return sqlSession.delete("post.delete", vo) == 1;
	}
	
	// 포스트를 카테고리 별로 삭제하기. 그전에 user_id 여부를 확인한다.
	// category.xml에서 해당 카테고리를 삭제하기 전에 여기서 먼저 실행한다!
	// [category_no] >>> process count
	public Boolean deleteByCategoryNo(PostVO vo) {
		return sqlSession.delete("post.deleteByCategoryNo", vo) >= 0;
	}
	
	// 포스트 목록 list 조회
	// 하나의 포스트 내용 view 밑에 목록 전체를 출력하는 용도로 쓰인다.
	// [category_no] >>> list [no, title, reg_date(년/월/일 까지만), category_no)]
	public List<PostVO> findAll(PostVO vo){
		List<PostVO> result = sqlSession.selectList("post.findAll", vo);
		return result;
	}
	
	// 해당 포스트 내용 조회
	// [no, category_no] >>> [no, title, contents, category_no]
	public PostVO find(PostVO vo) {
		return (PostVO) sqlSession.selectOne("post.find", vo);
	}
		
}
