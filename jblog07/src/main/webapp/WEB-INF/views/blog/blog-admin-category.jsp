<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${blogVo.title }</title>

<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script type="text/javascript">
var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
});
var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
});

var userId = "${blogVo.user_id }";

// 리스트 불러오는 함수
// table 내에 append 시킨다
var fetchList = function() {
	$.ajax({
		url: "${pageContext.request.contextPath }/api/category/list/" + userId,
		async: true,
		type: 'get',
		dataType: 'json',
		data: '',
		success: function(response){
			// 데이터를 불러오지 못한 경우...
			if(response.result == "fail"){
				console.error("데이터를 불러오지 못했습니다.");
				console.error(response.message);
				return;
			}
			
			response.pageContext = "${pageContext.request.contextPath}";
			console.log(response);
			
			// 리스트 불러오기
			var html = listTemplate.render(response);
			$(".admin-cat").last().append(html);
			
		},
		error: function(xhr, status, e) {
			console.error(status + ":" + e);
		}
	});
};


// jQuery 종합 설정
$(function() {
	// 추가
	$("#admin-cat-addform").submit(function(event) {
		event.preventDefault();
		
		var vo = {};
		vo.name = $("#input-name").val();
		if(vo.name == ''){
			alert("카테고리 이름을 입력하세요.");
			$("#input-name").focus();
			return;
		}
		
		vo.description = $("#input-description").val();
		if(vo.description == ''){
			alert("카테고리 설명 내용을 입력하세요.");
			$("#input-description").focus();
			return;
		}
		
		console.log(vo);
		
		$.ajax({
			url: "${pageContext.request.contextPath }/api/category/add/" + userId,
			async: true,
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response){
				// 데이터를 불러오지 못한 경우...
				if(response.result == "fail"){
					console.error("데이터를 불러오지 못했습니다.");
					console.error(response.message);
					return;
				}
				
				response.pageContext = "${pageContext.request.contextPath}";
				console.log(response);
				
				// 리스트 불러오기
				var html = listTemplate.render(response);
				$(".admin-cat").last().append(html);
				
				// 마지막으로 추가 form을 비우기
				$("#admin-cat-addform")[0].reset();
			},
			error: function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	});
	
	// 삭제 "<img/>" 태크를 클릭했을 때
	$(document).on("click", "img#img-delete", function() {
		var no = $(this).data("no");
		
		$.ajax({
			url : "${pageContext.request.contextPath }/api/category/delete/" + userId,
			async : true,
			type : 'post',
			dataType : 'json',
			data : 'no=' + no,
			success : function(response) {
				// 데이터를 불러오지 못한 경우...
				if(response.result == "fail"){
					console.error("데이터를 불러오지 못했습니다.");
					console.error(response.message);
					return;
				}
				
				response.data.pageContext = "${pageContext.request.contextPath}";

				// 삭제가 되었다는 의미이므로 .remove() 실행
				$(".admin-cat #category_" + no).remove();
			},
			error : function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	});
	
	// 리스트 불러오는 함수
	// table 내에 append 시킨다
	fetchList();
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/${blogVo.user_id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/${blogVo.user_id}/admin/write">글작성</a></li>
				</ul>
				
				<!-- '.admin-cat' -->
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		
		      		<!-- 카테고리 목록 표시 -->
		      					
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			
      			<!-- '#admin-cat-addform' -->
				<form id="admin-cat-addform" action='' method='post'>
					<table>
						<tr>
							<td class="t">카테고리명</td>
							<td><input type="text" id='input-name' name="name" value=""></td>
						</tr>
						<tr>
							<td class="t">설명</td>
							<td><input type="text" id='input-description' name="description" value=""></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input type="submit" value="카테고리 추가"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/blog-footer-div.jsp"></c:import>
	</div>
</body>
</html>