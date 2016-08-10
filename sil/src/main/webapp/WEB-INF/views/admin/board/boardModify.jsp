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

	<form id="frm" enctype="multipart/form-data">
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
					<td>
						${map.favority_id }
						<input type="hidden" id="favority_id" name="favority_id" value="${map.favority_id }">
					</td>
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
					<th scope="row">제목</th>
					<td colspan="3">
						<input type="text" id="favority_nm" name="favority_nm" class="wdp_90" value="${map.favority_nm }"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="view_text">
						<textarea rows="20" cols="100" title="내용" id="favority_cmt" name="favority_cmt">${map.favority_cmt }</textarea>
					</td>
				</tr>
				
				<tr>
					<th scope="row">첨부파일</th>
					<td colspan="3">
						<div id="fileDiv">				
							<c:forEach var="row" items="${list }" varStatus="var">
								<p>
									<input type="hidden" id="file_id" name="file_id_${var.index }" value="${row.file_id }">
									<a href="#this" id="originalFileName_${var.index }" name="originalFileName_${var.index }">${row.originalFileName }</a>
									<input type="file" id="file_${var.index }" name="file_${var.index }"> 
									(${row.file_size }kb)
									<a href="#this" class="btn" id="delete_${var.index }" name="delete_${var.index }">삭제</a>
								</p>
							</c:forEach>
						</div>
					</td>
				</tr>
				
			</tbody>
		</table>
	</form>
	
	<a href="#this" class="btn" id="addFile">파일 추가</a>
	<a href="#this" class="btn" id="list">목록으로</a>
	<a href="#this" class="btn" id="update">저장하기</a>
	<a href="#this" class="btn" id="delete">삭제하기</a>
	
	<c:import url="/include.do?fileName=/admin/include/body" />
	<script type="text/javascript">
	
		var gfv_count = '${fn:length(list)+1}';

		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#update").on("click", function(e){ //저장하기 버튼
				e.preventDefault();
				fn_updateBoard();
			});
			
			$("#delete").on("click", function(e){ //삭제하기 버튼
				e.preventDefault();
				fn_deleteBoard();
			});
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name^='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});

		});
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/admin/board/boardList.do' />");
			comSubmit.submit();
		}
		
		function fn_updateBoard(){
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/admin/board/boardUpdate.do' />");
			comSubmit.submit();
		}
		
		function fn_deleteBoard(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/admin/board/boardDelete.do' />");
			comSubmit.addParam("favority_id", $("#favority_id").val());
			comSubmit.submit();
			
		}
		
		function fn_addFile(){
			var str = "<p>" +
					"<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"'>"+
					"<a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'>삭제</a>" +
				"</p>";
			$("#fileDiv").append(str);
			$("#delete_"+(gfv_count++)).on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
	</script>
</body>
</html>
