package com.douzone.jblog.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.jblog.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);

	// 모든 Exception 들은 여기로 들어오게 된다
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws Exception {
		// 1. logging (파일다가 기록 or request에 담아서 출력)
		StringWriter writer = new StringWriter(); // buffer
		e.printStackTrace(new PrintWriter(writer));

		request.setAttribute("errorInfo", writer.toString());
		LOG.error("com.douzone.jblog.exception -> GlobalExceptionHandler.handlerException() envoked.");

		// 2. 요청 구분
		// 만약, JSON 요청인 경우에는 request header의 Accpet에 application/json
		// 만약, HTML 요청인 경우에는 request header의 Accpet에 text/html
		// 만약, jpeg 요청인 경우에는 request header의 Accpet에 image/jpeg
		String accept = request.getHeader("accept");

		if (accept.matches(".*application/json.*")) {
			// 3.JSON 응답
			response.setStatus(HttpServletResponse.SC_OK);

			JsonResult jsonResult = JsonResult.fail(writer.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);

			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
		} else {
			// 3. 안내페이지 가기(정상종료)
			request.setAttribute("exception", writer.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
	}

}
