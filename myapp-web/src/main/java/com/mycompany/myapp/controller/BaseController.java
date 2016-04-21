package com.mycompany.myapp.controller;

import com.mycompany.myapp.service.common.Result;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
	@Resource
	protected HttpServletRequest request;
	@Resource
	protected HttpSession session;
	
	protected <T> String getView(Result<T> res, Model model, String expectedViewName, CallBack cb){
		if(res.isSuccess()){
			cb.success(res);
		}else{
			model.addAttribute("code",res.getCode());
			model.addAttribute("msg",res.getMsg());
			return null;
		}
		return expectedViewName;
	}

	public interface CallBack{
		public <T> void success(Result<T> res);
	}
}
