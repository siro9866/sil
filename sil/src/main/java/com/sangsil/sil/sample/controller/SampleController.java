package com.sangsil.sil.sample.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sangsil.sil.common.paging.pagination.PaginationInfo;
import com.sangsil.sil.common.util.ComMap;
import com.sangsil.sil.sample.service.SampleService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SampleController {

	Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "sampleService")
	private SampleService sampleService;

	/**
	 * 리스트 - paging:전자정부프레임워크 이용
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/sampleList.do")
	public ModelAndView openSampleBoardList(ComMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/sampleList");
		
		Map<String,Object> resultMap = sampleService.selectBoardList(commandMap.getMap());
		
		mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
		mv.addObject("list", resultMap.get("result"));
		
		return mv;
	}
	
	/**
	 * 리스트 - paging:Ajax 이용
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/sampleListA.do")
	public ModelAndView openSampleBoardListA(ComMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/sampleListA");
		return mv;
	}
	@RequestMapping(value="/sample/selectBoardList.do")
	public ModelAndView selectBoardList(ComMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String,Object>> list = sampleService.selectBoardListA(commandMap.getMap());
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
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/sampleWrite.do")
	public ModelAndView openBoardWrite(ComMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/sampleWrite");
		return mv;
	}
	
	/**
	 * 등록
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/insertSample.do")
	public ModelAndView insertSample(ComMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/sampleList.do");
		
		sampleService.insertBoard(commandMap.getMap(), request);
		
		return mv;
	}

	/**
	 * 상세
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/sampleDetail.do")
	public ModelAndView openBoardDetail(ComMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/sampleDetail");
		
		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}

	/**
	 * 수정폼
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/sampleModify.do")
	public ModelAndView openBoardUpdate(ComMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/sampleModify");
		
		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}

	/**
	 * 수정
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/sampleUpdate.do")
	public ModelAndView updateBoard(ComMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/sampleDetail.do");
		
		sampleService.updateBoard(commandMap.getMap(), request);
		
		mv.addObject("favority_id", commandMap.get("favority_id"));
		mv.addObject("file_id", commandMap.get("file_id"));
		
		return mv;
	}

	/**
	 * 삭제
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/sampleDelete.do")
	public ModelAndView deleteBoard(ComMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/sampleList.do");
		
		sampleService.deleteBoard(commandMap.getMap());
		
		return mv;
	}

	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(ComMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("");
		
//		if(commandMap.isEmpty() == false){
//			Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
//			Entry<String,Object> entry = null;
//			while(iterator.hasNext()){
//				entry = iterator.next();
//				logger.debug("key : "+entry.getKey()+", value : "+entry.getValue());
//			}
//		}
		return mv;
	}


}
