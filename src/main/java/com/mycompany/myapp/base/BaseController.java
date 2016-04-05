package com.mycompany.myapp.base;

import com.google.common.base.Preconditions;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
	@Resource
	protected HttpServletRequest request;
	@Resource
	protected HttpSession session;
	
	protected <T> String getView(Result<T> res,Model model,String expectedViewName,CallBack cb){
		if(res.isSuccess()){
			cb.success(res);
		}else{
			model.addAttribute("code",res.getCode());
			model.addAttribute("msg",res.getMsg());
			return null;
		}
		return expectedViewName;
	}
	
	protected void checkParams(Object... objs){
		if(objs==null)return;
		for(Object o : objs){
			Preconditions.checkNotNull(o,"必要参数不在");
		}
	}

	public interface CallBack{
		public <T> void success(Result<T> res);
	}
}
