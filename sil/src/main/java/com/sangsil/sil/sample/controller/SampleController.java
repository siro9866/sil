package com.sangsil.sil.sample.controller;

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

import com.sangsil.sil.common.common.service.CommonService;
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
	@RequestMapping(value = "/sample/sampleList.do")
	public ModelAndView openSampleBoardList(ComMap comMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/sampleList");
		
		Map<String,Object> resultMap = sampleService.selectBoardList(comMap.getMap());
		
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
	@RequestMapping(value = "/sample/sampleListA.do")
	public ModelAndView openSampleBoardListA(ComMap comMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/sampleListA");
		return mv;
	}
	@RequestMapping(value="/sample/selectBoardList.do")
	public ModelAndView selectBoardList(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String,Object>> list = sampleService.selectBoardListA(comMap.getMap());
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
	@RequestMapping(value="/sample/sampleWrite.do")
	public ModelAndView openBoardWrite(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/sampleWrite");
		return mv;
	}
	
	/**
	 * 등록
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/insertSample.do")
	public ModelAndView insertSample(ComMap comMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/sampleList.do");
		
		// 오토인크리먼트 값 구해서 파일 인서트시 참조
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("tablesc", TABLESC);
		pMap.put("table_t_favority", TABLE_T_FAVORITY);
		String board_id = commonService.getAutoIncreamentId(pMap);
		// 임시-파일저장시 아이디를 알아야 하는데 오토인크리라 코드 추가되어야함
		comMap.put("board_id", board_id);
		
		sampleService.insertBoard(comMap.getMap(), request);
		
		return mv;
	}

	/**
	 * 상세
	 * @param comMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample/sampleDetail.do")
	public ModelAndView openBoardDetail(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/sampleDetail");
		
		Map<String,Object> map = sampleService.selectBoardDetail(comMap.getMap());
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
	@RequestMapping(value="/sample/sampleModify.do")
	public ModelAndView openBoardUpdate(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/sampleModify");
		
		Map<String,Object> map = sampleService.selectBoardDetail(comMap.getMap());
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
	@RequestMapping(value="/sample/sampleUpdate.do")
	public ModelAndView updateBoard(ComMap comMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/sampleDetail.do");
		
		sampleService.updateBoard(comMap.getMap(), request);
		
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
	@RequestMapping(value="/sample/sampleDelete.do")
	public ModelAndView deleteBoard(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/sampleList.do");
		
		sampleService.deleteBoard(comMap.getMap());
		
		return mv;
	}

	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(ComMap comMap) throws Exception{
		ModelAndView mv = new ModelAndView("");
		
//		if(comMap.isEmpty() == false){
//			Iterator<Entry<String,Object>> iterator = comMap.getMap().entrySet().iterator();
//			Entry<String,Object> entry = null;
//			while(iterator.hasNext()){
//				entry = iterator.next();
//				logger.debug("key : "+entry.getKey()+", value : "+entry.getValue());
//			}
//		}
		return mv;
	}


}
