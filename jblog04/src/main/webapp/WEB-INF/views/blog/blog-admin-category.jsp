<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${blogVo.title }</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
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
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
					<c:forEach items='${categoryList }' var='categoryVo' varStatus='status'>
						<tr>
							<td>${status.count }</td>
							<td>${categoryVo.name }</td>
							<td>${categoryVo.posted }</td>
							<td>${categoryVo.description }</td>
							<td>
								<img
								onclick="javascript:location.href='${pageContext.request.contextPath}/${blogVo.user_id }/admin/category/remove/${categoryVo.no }';"
								src="${pageContext.request.contextPath}/assets/images/delete.jpg">
							</td>
						</tr>
					</c:forEach>
					
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			
				<form action="${pageContext.request.contextPath}/${blogVo.user_id }/admin/category/add" method="post">
					<table id="admin-cat-add">
						<tr>
							<td class="t">카테고리명</td>
							<td><input type="text" name="name" value="${previousVo.name }"></td>
						</tr>
						<tr>
							<td class="t">설명</td>
							<td><input type="text" name="description" value="${previousVo.description }"></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input type="submit" value="카테고리 추가"></td>
						</tr>
					</table>
					<c:if test="${not empty previousVo }">
      					<p>카테고리 추가에 실패했습니다. 누락 여부 등을 다시 확인해주세요!</p>
      				</c:if>
				</form>
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/blog-footer-div.jsp"></c:import>
	</div>
</body>
</html>