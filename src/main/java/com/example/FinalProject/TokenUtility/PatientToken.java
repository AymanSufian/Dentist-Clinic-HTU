package com.example.FinalProject.TokenUtility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.FinalProject.POJO.ResultPojo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class PatientToken {

	
	public final Integer expDate = 60000;
	public final String keySign = "Test";

	public String generateToken(String username) {
		// Claims
		// Experience
		// sign

		Map<String, Object> test = new HashMap<String, Object>();
		test.put("username", username);

		// 60000 1min
		// Date convertedDatetime = new Date(System.currentTimeMillis() + expDate);

		return Jwts.builder().setClaims(test)
				// .setExpiration(convertedDatetime)
				.signWith(SignatureAlgorithm.HS512, keySign).compact();
	}

	public ResultPojo checkToken(String token) {
		ResultPojo result = new ResultPojo();

		Map<String, Object> resultMap = new HashMap<>();
		try {
			Claims s = Jwts.parser().setSigningKey(keySign).parseClaimsJws(token).getBody();
			result.setStatusCode("Success");
			String username = (String) s.get("username");
			resultMap.put("username", username);
			result.setResultMap(resultMap);
			return result;
		} catch (SignatureException ex) {
			result.setStatusCode("Failed");
			resultMap.put("error", "Invalid JWT signature");
			result.setResultMap(resultMap);
			return result;

		} catch (MalformedJwtException ex) {
			result.setStatusCode("Failed");
			resultMap.put("error", "Invalid JWT token");
			result.setResultMap(resultMap);
			return result;

		} catch (ExpiredJwtException ex) {
			result.setStatusCode("Failed");
			resultMap.put("error", "Expired JWT token");
			result.setResultMap(resultMap);
			return result;

		} catch (UnsupportedJwtException ex) {
			result.setStatusCode("Failed");
			resultMap.put("error", "Unsupported JWT token");
			result.setResultMap(resultMap);
			return result;

		}

		catch (IllegalArgumentException ex) {
			result.setStatusCode("Failed");
			resultMap.put("error", "JWT string is empty");
			result.setResultMap(resultMap);
			return result;

		} catch (Exception e) {
			result.setStatusCode("Failed");
			resultMap.put("error", e.getMessage());
			result.setResultMap(resultMap);
			return result;
		}

	}

	
	
}
