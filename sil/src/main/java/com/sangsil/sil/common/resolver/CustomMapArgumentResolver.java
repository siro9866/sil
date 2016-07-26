package com.sangsil.sil.common.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sangsil.sil.common.util.ComMap;

public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		ComMap commandMap = new ComMap();
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Enumeration<?> enumeration = request.getParameterNames();
		
		logger.debug("S:PARAM_CHECK++++++++++++++++++++++++++++++++++++");
		String key = null;
		String[] values = null;
		while(enumeration.hasMoreElements()){
			key = (String) enumeration.nextElement();
			values = request.getParameterValues(key);
			if(values != null){
				commandMap.put(key, (values.length > 1) ? values:values[0] );
				logger.debug("key:"+key+", value:"+values[0]);
			}
		}
		logger.debug("E:PARAM_CHECK++++++++++++++++++++++++++++++++++++");
		return commandMap;

	}

	/**
	 * Resolver가 적용 가능한지 검사하는 역할을 하고, resolverArgument 메서드는 파라미터와 기타 정보를 받아서 실제 객체를 반환한다.
	 *	supportsparameter 메서드는 컨트롤러의 파라미터가 CommandMap 클래스인지 검사하도록 하였다. 
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return ComMap.class.isAssignableFrom(parameter.getParameterType());
	}

}
