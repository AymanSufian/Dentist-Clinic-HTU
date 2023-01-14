package com.example.FinalProject.Security;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.FinalProject.POJO.ResultPojo;
import com.example.FinalProject.TokenUtility.TokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class Doctor_Service_Interceptor implements HandlerInterceptor {
	
	@Autowired
	private TokenUtility tokenUtility;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		ResultPojo result = new ResultPojo();
		Map<String, Object> resultMap = new HashMap<>();
		if (token == null || token.isEmpty()) {
			result.setStatusCode(token);
			resultMap.put("token", "token cannot be null or empty,please put the token in header");
			result.setResultMap(resultMap);
			// return result;
			String finalResult = mapper.writeValueAsString(result);
			response.setStatus(401);
			response.setContentType("application/json");

			try (PrintWriter writer = response.getWriter()) {
				writer.write(finalResult);
			}
			return false;
		} else {
			ResultPojo resultToken = tokenUtility.checkToken(token);
			if (resultToken.getStatusCode().equalsIgnoreCase("Success")) {
				return true;
			} else {
				String finalResult = mapper.writeValueAsString(resultToken);
				response.setStatus(401);
				response.setContentType("application/json");

				try (PrintWriter writer = response.getWriter()) {
					writer.write(finalResult);
				}
				return false;
			}

		}

	}

}
