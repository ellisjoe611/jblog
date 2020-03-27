<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>

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
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty selectedPostVo }">
							<h4>포스트가 조회되지 않습니다.</h4>
						</c:when>
						<c:otherwise>
							<h4>${selectedPostVo.title }</h4>
							<p>
								${fn:replace(selectedPostVo.contents, newLine, "<br>") }
							<p>
						</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:choose>
						<c:when test="${empty postList }">
							<h1>해당 카테고리에서 등록된 포스트가 없습니다.</h1>
						</c:when>
						<c:otherwise>
							<c:forEach items='${postList }' var='postVo' varStatus='status'>
								<li><a href="${pageContext.request.contextPath }/${blogVo.user_id }/${postVo.category_no }/${postVo.no }">${postVo.title }</a> <span>${postVo.reg_date }</span>	</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/images/${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items='${categoryList }' var='categoryVo' varStatus='status'>
					<li><a href="${pageContext.request.contextPath }/${blogVo.user_id }/${categoryVo.no }">${categoryVo.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/blog-footer-div.jsp"></c:import>
	</div>
</body>
</html>