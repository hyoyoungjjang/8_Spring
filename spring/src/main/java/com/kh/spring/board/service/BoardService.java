package com.kh.spring.board.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {
	
	//게시글 총 갯수 가져오기
	public int selectListCount();
	
	//게시글 리스트 조회
	public ArrayList<Board> selsecList(PageInfo pi);
	
	//게시글 조회수 증가
	public int increaseCount(int bno);
	
	//게시글정보 조회
	public Board selectBoard(int bno);
	
	//댓글리스트 조회
	public ArrayList<Reply> selectReply(int bno);
}