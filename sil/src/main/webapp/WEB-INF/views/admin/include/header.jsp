<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/include/taglibs.jsp" %>


<!-- SPRING SECURITY -->
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="com.sangsil.sil.common.security.MemberInfo" %>
<%
Authentication auth = SecurityContextHolder.getContext().getAuthentication();

Object principal = auth.getPrincipal();
String name = "";
if(principal != null && principal instanceof MemberInfo){
	name = ((MemberInfo)principal).getName();
}
%>
<!-- SPRING SECURITY -->



<!-- S:FILE:header.jsp -->
<div style="width:200px; float:left;">
<!-- 로그인 하지 않은 경우 다음 코드 보여줌 -->
	<sec:authorize access="isAnonymous()">
		<form id="frm" name="frm" method="POST" action="${ctx}/j_spring_security_check">
		<table>
			<tr>
				<td style="width:50px;">id</td>
				<td style="width:150px;">
					<input style="width:145px;" type="text" id="login_id" name="login_id" value="" />
				</td>
				 
			</tr>
			<tr>
				<td>pwd</td>
				<td>
					<input style="width:145px;" type="text" id="login_pw" name="login_pw" value="" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" id="login_btn" value="로그인" />
				</td>
			</tr>
		</table>
		</form>
	</sec:authorize>
	
<!-- 로그인 한 경우 다음 코드 보여줌 -->	
	<sec:authorize access="isAuthenticated()">
		<%=name%>님 반갑습니다
		<a href="${ctx}/j_spring_security_logout">로그아웃</a>
	</sec:authorize>
	
	<ul>
		<sec:authorize access="hasRole('ADMIN')">
			<li>관리자 화면</li>
		</sec:authorize>
		<sec:authorize access="permitAll">
			<li>비회원 게시판</li>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<li>준회원 게시판</li>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
			<li>정회원 게시판</li>
		</sec:authorize>
	</ul>
</div>
<!-- E:FILE:header.jsp -->