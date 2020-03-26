package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVO;

@Repository
public class BlogRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// blog 추가하기 (반드시 user 추가 후 실행)
	// [user_id, title, logo] >>> process count
	public Boolean insert(BlogVO vo) {
		return sqlSession.insert("blog.insert", vo) == 1;
	}
	
	// 블로그 업데이트
	// [title, logo(??), user_id] >>> process count
	public Boolean update(BlogVO vo) {
		return sqlSession.update("blog.update", vo) == 1;
	}
	
	// blog 정보 조회
	// [user_id] >>> [user_id, title, logo]
	public BlogVO find(BlogVO vo) {
		return sqlSession.selectOne("blog.find", vo);
	}
	
}
