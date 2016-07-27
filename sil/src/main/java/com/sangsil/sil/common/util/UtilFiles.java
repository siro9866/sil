package com.sangsil.sil.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils")
public class UtilFiles {
//	private static final String filePath = "/etcc/FILES/";
	@Value("#{config['FILEUPLOADPATH']}") String FILEUPLOADPATH;
	
	/**
	 * 파일저장: 다중파일업로드 가능하다
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> parseInsertFileInfo(Map<String, Object> map, HttpServletRequest request)
			throws Exception {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap = null;
		
		//실제 업로드될 파일명
		Calendar calendar = Calendar.getInstance();
		//연별로 디렉토리 생성
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		//연별로 디렉토리 생성
		String dateDirectory = dateFormat.format(calendar.getTime());

		String board_id = (String) map.get("board_id");
		
		// 파일경로
		StringBuffer sbFilePath = new StringBuffer();
		sbFilePath.append(File.separatorChar);
		sbFilePath.append(FILEUPLOADPATH);
		sbFilePath.append(File.separatorChar);
		sbFilePath.append(map.get("fileUploadPath"));
		sbFilePath.append(File.separatorChar);
		sbFilePath.append(dateDirectory);
		sbFilePath.append(File.separatorChar);
		
		String filePath = sbFilePath.toString();
		
		File file = new File(filePath);
		if (file.exists() == false) {
			file.mkdirs();
		}

		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = UtilCommons.getRandomString() + originalFileExtension;

				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file);

				listMap = new HashMap<String, Object>();
				listMap.put("board_gbn", "BAA009");
				listMap.put("board_id", board_id);
				listMap.put("originalFileName", originalFileName);
				listMap.put("file_name", storedFileName);
				listMap.put("file_size", multipartFile.getSize());
				listMap.put("path_name", filePath);
				listMap.put("view_path_name", filePath);
				listMap.put("file_ext", originalFileExtension);
				list.add(listMap);
			}
		}
		return list;
	}

	/**
	 * 파일 수정기능
	 * 게시글에 등록된 파일을 모두 삭제 처리하고 최종 저장시 신규파일과 
	 * 기존파일을 구분하여 재등록 처리함(DB값만 변경 실제 파일 삭제 하지 않음)
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null; 
		
		String board_id = (String)map.get("board_id");
		String requestName = null;
		String idx = null;
		
		//실제 업로드될 파일명
		Calendar calendar = Calendar.getInstance();
		//연별로 디렉토리 생성
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		//연별로 디렉토리 생성
		String dateDirectory = dateFormat.format(calendar.getTime());

		// 파일경로
		StringBuffer sbFilePath = new StringBuffer();
		sbFilePath.append(File.separatorChar);
		sbFilePath.append(FILEUPLOADPATH);
		sbFilePath.append(File.separatorChar);
		sbFilePath.append(map.get("fileUploadPath"));
		sbFilePath.append(File.separatorChar);
		sbFilePath.append(dateDirectory);
		sbFilePath.append(File.separatorChar);
		
		String filePath = sbFilePath.toString();
		
		while(iterator.hasNext()){
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false){
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = UtilCommons.getRandomString() + originalFileExtension;
				
				multipartFile.transferTo(new File(filePath + storedFileName));
				
				listMap = new HashMap<String,Object>();
				listMap.put("IS_NEW", "Y");
				listMap.put("board_gbn", "BAA009");
				listMap.put("board_id", board_id);
				listMap.put("originalFileName", originalFileName);
				listMap.put("file_name", storedFileName);
				listMap.put("file_size", multipartFile.getSize());
				listMap.put("path_name", filePath);
				listMap.put("view_path_name", filePath);
				listMap.put("file_ext", originalFileExtension);
				list.add(listMap);
			}
			else{
				requestName = multipartFile.getName();
				idx = "file_id_"+requestName.substring(requestName.indexOf("_")+1);
				if(map.containsKey(idx) == true && map.get(idx) != null){
					listMap = new HashMap<String,Object>();
					listMap.put("IS_NEW", "N");
					listMap.put("file_id", map.get(idx));
					list.add(listMap);
				}
			}
		}
		return list;
	}

	
}
