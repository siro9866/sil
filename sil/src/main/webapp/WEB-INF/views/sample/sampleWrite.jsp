<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/sample/include/taglibs.jsp" %>

<c:import url="/include.do?fileName=/sample/include/doctype"/>
<c:import url="/include.do?fileName=/sample/include/style"/>
<c:import url="/include.do?fileName=/sample/include/script"/>

</head>
<body>

<!-- S:FILE:header.jsp -->
<c:import url="/include.do?fileName=/sample/include/header&depth1=menuL1&depth2=menuL1"/>
<!-- E:FILE:header.jsp -->

	<form id="frm" enctype="multipart/form-data">
		<table class="board_view">
			<colgroup>
				<col width="15%">
				<col width="*"/>
			</colgroup>
			<caption>게시글 작성</caption>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" id="favority_nm" name="favority_nm" class="wdp_90"></input></td>
				</tr>
				<tr>
					<th scope="row">URL</th>
					<td><input type="text" id="favority_url" name="favority_url" class="wdp_90"></input></td>
				</tr>
				<tr>
					<td colspan="2" class="view_text">
						<textarea rows="20" cols="100" title="내용" id="favority_cmt" name="favority_cmt"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		
		<div id="fileDiv">
			<p>
				<input type="file" id="file" name="file_0">
				<a href="#this" class="btn" id="delete" name="delete">삭제</a>
			</p>
		</div>


		<br/><br/>
		
		<a href="#this" class="btn" id="addFile">파일 추가</a>
		<a href="#this" class="btn" id="write" >작성하기</a>
		<a href="#this" class="btn" id="list" >목록으로</a>
	</form>
	
	<c:import url="/include.do?fileName=/sample/include/body" />

	<script type="text/javascript">
		var gfv_count = 1;
		$(document).ready(function(){
			$("#list").on("click", function(e){
				e.preventDefault();
				fn_sampleList();
			});
			
			//작성하기 버튼
			$("#write").on("click", function(e){ 
				e.preventDefault();
				fn_insertSample();
			});

			//파일 추가 버튼
			$("#addFile").on("click", function(e){ 
				e.preventDefault();
				fn_addFile();
			});

		});
		
		function fn_sampleList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/sampleList.do' />");
			comSubmit.submit();
		}
		function fn_insertSample(){
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/sample/insertSample.do' />");
			comSubmit.submit();
		}
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'><a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
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
