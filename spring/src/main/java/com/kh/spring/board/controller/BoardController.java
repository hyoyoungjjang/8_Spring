package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
		int boardCount = boardService.selectListCount();
		PageInfo pi = Pagination.getPageInfo(boardCount, currentPage, 10, 5);
		ArrayList<Board> list = boardService.selsecList(pi);
		
		model.addAttribute("list", list);
		model.addAttribute("pi" ,pi);
		return "board/boardListView";
	}
	
	@RequestMapping(value = "detail.bo")
	public String selectBoard(int bno, Model model) {
		
		int result = boardService.increaseCount(bno);
		
		if(result > 0) {
			Board b = boardService.selectBoard(bno);
			model.addAttribute("b", b);
			
			return "board/boardDetailView";
		} else {
			model.addAttribute("errorMsg", "게시글 조회 실패");
			return "common/errorPage";
		}		
	}
	
	@ResponseBody
	@RequestMapping(value = "rlist.bo", produces="apllication/json; charset-UTF-8")
	public String ajaxSelectReplyList(int bno) {
		
		ArrayList<Reply> list = boardService.selectReply(bno);
		
		return new Gson().toJson(list);
	}
	
}
