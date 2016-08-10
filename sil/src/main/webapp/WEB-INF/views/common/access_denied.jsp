<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@ page import="org.springframework.security.core.userdetails.UserDetailsService"%>

<%@ include file="/WEB-INF/views/admin/include/taglibs.jsp"%>

<c:import url="/include.do?fileName=/admin/include/doctype" />
<c:import url="/include.do?fileName=/admin/include/style" />
<c:import url="/include.do?fileName=/admin/include/script" />

<style>
table {
	width: 800px;
}

table, th, td {
	border-collapse: collapse;
	border: 1px solid gray;
}
</style>

</head>

<body>

<!-- S:FILE:header.jsp -->
<c:import url="/include.do?fileName=/admin/include/header&depth1=menuL1&depth2=menuL1"/>
<!-- E:FILE:header.jsp -->

	<div style="display: inline-block;">
		<div style="float: right;">
			접근권한이 없습니다. 담당자에게 문의하여 주시기 바랍니다. ${SPRING_SECURITY_403_EXCEPTION}
			<br>errormsg: ${errormsg }
			<br>username: ${username }
			<br>
			
			<a href="<c:url value='/index.jsp'/>">확인</a>
		</div>
	</div>




	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>



</body>
</html>
