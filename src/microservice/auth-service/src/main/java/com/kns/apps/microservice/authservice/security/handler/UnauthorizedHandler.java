package com.kns.apps.microservice.authservice.security.handler;//package vn.com.kns.portalapi.web.security.handler;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.Serializable;
//
//public class UnauthorizedHandler implements AuthenticationEntryPoint, Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//	}
//}
