package com.mycompany.myapp.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
  @Override  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
      
	  String uri = request.getRequestURI();
	  
	  String suffixs = ".js|.css|.ico|.png|.jpg|.bmp";
	  String loginUri = "/login";
	  
	  if(excludeUrl(uri,suffixs) || uri.equals(loginUri)){
		  return true;
	  }
	 
	  if(request.getSession().getAttribute("login") == null){
		  response.sendRedirect(loginUri);
	  }
	  
	  return true;  
  }  
  
  private boolean excludeUrl(String uri,String excludes){
	  
	  for(String suffix : StringUtils.split(excludes,"\\|")){
		  if(uri.endsWith(suffix)){
			  return true;
		  }
	  }
	  
	  return false;
  }
}