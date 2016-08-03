<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>로그인페이지</title>
</head>

<body>
	<h2>로그인</h2>
	<form name="frm" method="post" action="/common/login/login.do">
		<table>
			<tr height="40px">
				<td>사용자아이디</td>
				<td><input type="text" name="user_id"></td>
			</tr>
			<tr height="40px">
				<td>패스워드</td>
				<td><input type="password" name="user_pw"></td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="center"><input type="submit" value="로그인"></td>
				<td align="center"><input type="reset" value="리셋"></td>
			</tr>
		</table>
	</form>
	
<script type="text/javascript">
if("${!empty rMsg}"){
	alert("${rMsg}");
}
</script>	
	
</body>
</html>