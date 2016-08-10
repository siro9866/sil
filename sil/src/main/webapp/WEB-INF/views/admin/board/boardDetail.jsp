<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/admin/include/taglibs.jsp" %>

<c:import url="/include.do?fileName=/admin/include/doctype"/>
<c:import url="/include.do?fileName=/admin/include/style"/>
<c:import url="/include.do?fileName=/admin/include/script"/>

</head>
<body>

<!-- S:FILE:header.jsp -->
<c:import url="/include.do?fileName=/admin/include/header&depth1=menuL1&depth2=menuL1"/>
<!-- E:FILE:header.jsp -->

	<table class="board_view">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<caption>게시글 상세</caption>
		<tbody>
			<tr>
				<th scope="row">글 번호</th>
				<td>${map.favority_id }</td>
				<th scope="row">조회수</th>
				<td>${map.hit_cnt }</td>
			</tr>
			<tr>
				<th scope="row">작성자</th>
				<td>${map.in_user }</td>
				<th scope="row">작성시간</th>
				<td>${map.in_date }</td>
			</tr>
			<tr>
				<th scope="row">URL</th>
				<td colspan="3">${map.favority_url }</td>
			</tr>
			<tr>
				<td colspan="4">${map.favority_cmt }</td>
			</tr>
			<tr>
				<th scope="row">첨부파일</th>
				<td colspan="3">
					<c:forEach var="row" items="${list }">
						<p>
							<input type="hidden" id="file_id" value="${row.file_id }">
							<a href="#this" name="file">${row.originalFileName }</a> 
							(${row.file_size }kb)
						</p>
					</c:forEach>
				</td>
			</tr>

		</tbody>
	</table>
	
	<a href="#this" class="btn" id="list">목록으로</a>
	<a href="#this" class="btn" id="update">수정하기</a>
	
	<c:import url="/include.do?fileName=/admin/include/body" />
	<script type="text/javascript">
		$(document).ready(function(){
			// 목록으로 버튼
			$("#list").on("click", function(e){ 
				e.preventDefault();
				fn_openBoardList();
			});
			
			// 수정으로 버튼
			$("#update").on("click", function(e){
				e.preventDefault();
				fn_openBoardUpdate();
			});
			
			// 파일
			$("a[name='file']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});

		});
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/admin/board/boardList.do' />");
			comSubmit.submit();
		}
		
		function fn_openBoardUpdate(){
			var favority_id = "${map.favority_id}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/admin/board/boardModify.do' />");
			comSubmit.addParam("favority_id", favority_id);
			comSubmit.submit();
		}
		function fn_downloadFile(obj){
			var file_id = obj.parent().find("#file_id").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("file_id", file_id);
			comSubmit.submit();
		}
	</script>
</body>
</html>
