<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/admin/include/taglibs.jsp"%>

<c:import url="/include.do?fileName=/admin/include/doctype" />
<c:import url="/include.do?fileName=/admin/include/style" />
<c:import url="/include.do?fileName=/admin/include/script" />

<title>Login Page 어드민</title>

</head>

<body onload='document.f.j_username.focus();'>
	<p>로그인 화면</p>
	<form name="frm" id="frm" action='/j_spring_security_check'
		method='POST'>

		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='login_id' id="login_id" value="${login_id }"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='login_pw' id="login_pw" value="${login_pw }"></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" id="login_btn"
					type="submit" value="Login"></td>
			</tr>

			<!-- 로그인 인증 실패시 나타남 -->
			<c:if test="${not empty param.fail}">
		        <tr>
					<td colspan="2">                
						<font color="red">
							<p>Your login attempt was not successful, try again.</p>
							<p>Reason:${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
							<p>${securityexceptionmsg }</p>
						</font>                 
					</td>
				</tr>
	        </c:if>

		</table>

		<input type="hidden" name="loginRedirect" value="${loginRedirect}" />

	</form>




	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#login_btn")
									.click(
											function() {
												if ($("#login_id").val() == "") {
													alert("로그인 아이디를 입력해주세요");
													$("#login_id").focus();
												} else if ($("#login_pw").val() == "") {
													alert("로그인 비밀번호를 입력해주세요");
													$("#login_pw").focus();
												} else {
													$("#frm")
															.attr("action",
																	"<c:url value='/j_spring_security_check'/>");
													$("#frm").submit();
												}
											});
						});
	</script>



</body>
</html>
