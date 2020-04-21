<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script type="text/javascript">
// jQuery 메인 시작
$(function(){
	// join-form 에서 제출 버튼을 눌렀을 때 입력 여부를 확인
	$('.join-form').submit(function(e){
		e.preventDefault();
		
		// name 이 누락된 경우
		if($('#name').val() == ''){
			alert('이름이 비어 있습니다.');
			$('#name').focus();
			return;
		}
		
		// id 가 누락된 경우
		if($('#id').val() == ''){
			alert('이름이 비어 있습니다.');
			$('#id').focus();
			return;
		}
		
		// id 중복채크를 안한 경우 (= 채크 이미지가 표기되지 않은 경우임)
		if($("#img-checkid").is(":hidden")){
			alert("ID 중복 채크를 하셨나요?");
			return;
		}
		
		// password 가 누락된 경우
		if($('#password').val() == ''){
			alert('비밀번호가 비어 있습니다.');
			$('#password').focus();
			return;
		}
		
		// 약관 동의를 안 한 경우
		if($("#agree-prov").is(":checked") == false){
			alert("약관 동의가 필요합니다.");
			return;
		}
		
		//위의 사항들이 지켜졌을 경우 form submit 실행
		this.submit();
	});
	
	// ID 중복 체크 완료 후 갑자기 이메일을 바꾸면
	// 중복 체크 상태를 원래로 되돌린다.
	$("#id").change(function(){
		$("#img-checkid").hide();
		$("#btn-checkid").show();
	});
	
	// ID 중복체크 실행
	$("#btn-checkid").click(function(){
		var id = $("#id").val();
		if(id == ''){
			return;
		}
		
		// ajax 통신용 json 설정
		$.ajax({
			url: '${pageContext.request.contextPath }/api/user/checkid?id=' + id,
			type: 'GET',
//			contentType: application/json,
			data: '',
			dataType: 'json',
			
			// 통신이 성공된 경우
			success: function(response){
				console.log(response);
				
				// 통신에 성공했지만 서버 상에서의 데이터 불러오는 데 실패한 경우
				if(response.result == "fail"){
					console.log(result.message);
					return;
				}
				
				// 서버 상에서의 데이터가 true(이미 존재하는 ID)로 나온 경우
				if(response.data == false){
					alert("이미 존재하는 ID 입니다.");
					$("#id").val('').focus();
					return;
				}
				
				// 여기에 내려왔다는 것은 사용자가 입력한 ID 사용이 가능하다는 의미임!!
				$("#btn-checkid").hide();
				$("#img-checkid").show();
			},
			
			// 통신이 실패된 경우
			error: function(XHR, status, e){
				console.error(status + " : " + e);
			}
		});
	
	});
	
});
</script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url="/WEB-INF/views/includes/index-menu.jsp"></c:import>
		
		<form class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath }/user/join">
			<c:if test="${not empty previousVo }">
				<p>회원가입에 실패했습니다. 빠진 것이 없는지 다시 확인해주세요.</p>
			</c:if>
			
			<label class="block-label" for="name">이름</label>
			<input id="name" name="name" type="text" value="${previousVo.name }">
			
			<label class="block-label" for="id">아이디</label>
			<input id="id" name="id" type="text" value="${previousVo.id }">
			
			<input id="btn-checkid" type="button" value="id 중복체크">
			<img id="img-checkid" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="pw" type="password" />

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>
</body>
</html>
