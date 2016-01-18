package com.mycompany.myapp.interceptor;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mycompany.myapp.domain.UserDO;

@Component
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
	  
	  for(String suffix : excludes.split("|")){
		  if(uri.endsWith(suffix)){
			  return true;
		  }
	  }
	  
	  return false;
  }
}