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

	<h2>게시판 목록</h2>
	<table class="board_list">
		<colgroup>
			<col width="10%"/>
			<col width="*"/>
			<col width="15%"/>
			<col width="20%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">글번호</th>
				<th scope="col">제목</th>
				<th scope="col">URL</th>
				<th scope="col">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(list) > 0}">
					<c:forEach items="${list }" var="row">
						<tr>
							<td>${row.favority_id }</td>
							<td class="title">
								<a href="#this" name="title">${row.favority_nm }</a>
								<input type="hidden" id="favority_id" value="${row.favority_id }">
							</td>
							<td>${row.favority_url }</td>
							<td>${row.in_date }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">조회된 결과가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	<div id="PAGE_NAVI"></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>

	
	<br/>
	<a href="#this" class="btn" id="write">글쓰기</a>
	
	<c:import url="/include.do?fileName=/sample/include/body" />
	<script type="text/javascript">
		$(document).ready(function(){
			
			fn_selectBoardList(1);
			$("#write").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();
				fn_openBoardWrite();
			});	
			
			$("a[name='title']").on("click", function(e){ //제목 
				e.preventDefault();
				fn_openBoardDetail($(this));
			});
		});
		
		
		function fn_openBoardWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/sampleWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_openBoardDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/sampleDetail.do' />");
			comSubmit.addParam("favority_id", obj.parent().find("#favority_id").val());
			comSubmit.submit();
		}
		
		function fn_search(pageNo){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/sampleListA.do' />");
			comSubmit.addParam("currentPageNo", pageNo);
			comSubmit.submit();
		}

		function fn_selectBoardList(pageNo){
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/sample/selectBoardList.do' />");
			comAjax.setCallback("fn_selectBoardListCallback");
			comAjax.addParam("PAGE_INDEX",pageNo);
			comAjax.addParam("PAGE_ROW", 15);
			comAjax.ajax();
		}
		
		function fn_selectBoardListCallback(data){
			
			alert("111");
			var total = data.TOTAL;
			var body = $("table>tbody");
			alert("total:"+total);
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
								"<td colspan='4'>조회된 결과가 없습니다.</td>" + 
							"</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					eventName : "fn_selectBoardList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				$.each(data.list, function(key, value){
					str += "<tr>" + 
								"<td>" + value.favority_id + "</td>" + 
								"<td class='title'>" +
									"<a href='#this' name='title'>" + value.favority_nm + "</a>" +
									"<input type='hidden' name='title' value=" + value.favority_id + ">" + 
								"</td>" +
								"<td>" + value.favority_url + "</td>" + 
								"<td>" + value.in_date + "</td>" + 
							"</tr>";
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_openBoardDetail($(this));
				});
			}
		}

	</script>	
</body>
</html>
