package com.sangsil.sil.admin.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sangsil.sil.admin.board.service.BoardService;
import com.sangsil.sil.common.common.service.CommonService;
import com.sangsil.sil.common.paging.pagination.PaginationInfo;
import com.sangsil.sil.common.util.ComMap;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {

	Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "boardService")
	private BoardService boardService;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Value("#{config['TABLESC']}") String TABLESC;
	@Value("#{config['TABLE_T_FAVORITY']}") String TABLE_T_FAVORITY;
	
	/**
	 * 리스트 - paging:전자정부프레임워크 이용
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/board/boardList")
	public ModelAndView boardList(ComMap comMap) throws Exception {
		ModelAndView mv = new ModelAndView("/admin/board/boardList");
		
		Map<String,Object> resultMap = boardService.boardList(comMap.getMap());
		
		mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
		mv.addObject("list", resultMap.get("result"));
		
		return mv;
	}
	
	/**
	 * 리스트 - paging:Ajax 이용
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/board/boardListA")
	public ModelAndView boardListA(ComMap comMap) throws Exception {
		ModelAndView mv = new ModelAndView("/admin/board/boardListA");
		return mv;
	}
	@RequestMapping(value="/admin/board/selectBoardList")
	public ModelAndView selectBoardListA(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String,Object>> list = boardService.selectBoardListA(comMap.getMap());
		mv.addObject("list", list);
		if(list.size() > 0){
			mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
		}
		else{
			mv.addObject("TOTAL", 0);
		}
		
		return mv;
	}


	/**
	 * 등록폼
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/board/boardWrite")
	public ModelAndView boardWrite(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("/admin/board/boardWrite");
		return mv;
	}
	
	/**
	 * 등록
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/board/insertBoard")
	public ModelAndView insertBoard(ComMap comMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/admin/board/boardList");
		
		// 오토인크리먼트 값 구해서 파일 인서트시 참조
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("tablesc", TABLESC);
		pMap.put("table_t_favority", TABLE_T_FAVORITY);
		String board_id = commonService.getAutoIncreamentId(pMap);
		// 임시-파일저장시 아이디를 알아야 하는데 오토인크리라 코드 추가되어야함
		comMap.put("board_id", board_id);
		
		boardService.insertBoard(comMap.getMap(), request);
		
		return mv;
	}

	/**
	 * 상세
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/board/boardDetail")
	public ModelAndView boardDetail(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("/admin/board/boardDetail");
		
		Map<String,Object> map = boardService.selectBoardDetail(comMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}

	/**
	 * 수정폼
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/board/boardModify")
	public ModelAndView boardModify(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("/admin/board/boardModify");
		
		Map<String,Object> map = boardService.selectBoardDetail(comMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}

	/**
	 * 수정
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/board/boardUpdate")
	public ModelAndView boardUpdate(ComMap comMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/admin/board/boardDetail");
		
		boardService.boardUpdate(comMap.getMap(), request);
		
		mv.addObject("favority_id", comMap.get("favority_id"));
		mv.addObject("file_id", comMap.get("file_id"));
		
		return mv;
	}

	/**
	 * 삭제
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/board/boardDelete")
	public ModelAndView boardDelete(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/admin/board/boardList");
		
		boardService.boardDelete(comMap.getMap());
		
		return mv;
	}
}
