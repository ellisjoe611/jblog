package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVO;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 유저 추가
	// [id, name, pw] >>> process count
	public Boolean insert(UserVO vo) {
		return sqlSession.insert("user.insert", vo) == 1;
	}
	
	// 로그인용
	// [id, pw] >>> [id, name]
	public UserVO findUser(UserVO vo) {
		return sqlSession.selectOne("user.findUser", vo);
	}

	public UserVO findUserId(String email) {
		return sqlSession.selectOne("user.findUserId", email);
	}
		
}
