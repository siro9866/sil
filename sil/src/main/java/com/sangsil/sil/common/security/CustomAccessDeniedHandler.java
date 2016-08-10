package com.sangsil.sil.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * ACCESS DENIED 핸들러 
 * Ajax 콜과 일반콜은 구분해야함
 * 
 * JSP 에서 AJAX 콜 샘플(헤더에 X-Ajax-call 유무로 판단)

	$.ajax({
	    url : "${ctx}/admin/ajaxTest",
	    data : {title : title, content : content},
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	    type : "POST",
	    dataType : "json",
	    beforeSend: function (xhr) {
	        xhr.setRequestHeader("X-Ajax-call", "true");
	    },
	    success:function(data, textStatus, jqXHR){
	        if(data.result == "OK"){
	            alert("ok");
	        }else{
	            alert("fail");
	        }
	    },
	    error:function(jqXHR, textStatus, errorThrown){
	        if(jqXHR.status == 403){
	            var response = $.parseJSON(jqXHR.responseText);
	            alert("result : " + response.result + "\nmessage : " + response.message);
	        }else{
	            alert(errorThrown);
	        }
	         
	    }
	});

 *
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// Ajax를 통해 들어온것인지 파악한다
		String ajaxHeader = request.getHeader("X-Ajax-call");
		String result = "";

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("UTF-8");

		if (ajaxHeader == null) { // null로 받은 경우는 X-Ajax-call 헤더 변수가 없다는 의미이기
									// 때문에 ajax가 아닌 일반적인 방법으로 접근했음을 의미한다
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				String username = ((UserDetails) principal).getUsername();
				request.setAttribute("username", username);
			}
			request.setAttribute("errormsg", accessDeniedException);
			request.getRequestDispatcher(errorPage).forward(request, response);
		} else {
			if ("true".equals(ajaxHeader)) { // true로 값을 받았다는 것은 ajax로 접근했음을
												// 의미한다
				result = "{\"result\" : \"fail\", \"message\" : \"" + accessDeniedException.getMessage() + "\"}";
			} else { // 헤더 변수는 있으나 값이 틀린 경우이므로 헤더값이 틀렸다는 의미로 돌려준다
				result = "{\"result\" : \"fail\", \"message\" : \"Access Denied(Header Value Mismatch)\"}";
			}
			response.getWriter().print(result);
			response.getWriter().flush();
		}
	}

	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}

}