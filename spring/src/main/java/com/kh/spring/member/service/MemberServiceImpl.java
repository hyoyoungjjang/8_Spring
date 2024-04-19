package com.kh.spring.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service //Conponent보다 더 구체화해서 Service been으로 등록시키는 것
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSessionTemplate sqlSession; // 기존의myBatis SqlSession객체 대체
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public Member loginMember(Member m) {
		//SqlSessionTemplate bean 등록후 @Autowired했음
		//스프링이 사용 후 자동으로 반납시켜주기 때문에 close메소드로 자원반납 할 필요가 없음
		//한줄로 기술이 가능
			
		return memberDao.loginMember(sqlSession, m);
	}

	@Override
	public int idCheck(String checkId) {
		
		return memberDao.idCheck(sqlSession, checkId);
	}

	@Override
	public int insertMember(Member m) {
		
		return memberDao.insertMember(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		
		return memberDao.updateMember(sqlSession, m);
		
	}

	@Override
	public int deleteMember(String userId) {
		
		return memberDao.deleteMember(sqlSession, userId);
	}

}
