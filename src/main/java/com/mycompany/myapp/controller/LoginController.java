package com.mycompany.myapp.controller;

import com.mycompany.myapp.base.BaseController;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.vo.LoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller  
@RequestMapping("/")
@SessionAttributes("aaa")
public class LoginController extends BaseController{
	@Resource  
    private UserService userService;  
	
	@RequestMapping(value="/login",method = RequestMethod.POST)  
    public String login(Model model,UserDO user){
		
		System.out.println(user.getAccount());
		return null;
		/*return getView(userService.login(account, password), model, "redirect:index",new CallBack() {

			@Override
			public <T> void success(Result<T> res) {
				session.setAttribute("login", res.getModel());
			}
		});*/
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        System.out.print("hello4");
        return "login";

    }

    @RequestMapping(value={"/index  "})
    public String toHome(Model model){
        LoginVO login = (LoginVO)session.getAttribute("user");
        model.addAttribute("login", login);
        return "index";


    }

	@RequestMapping(value="/test",params="add=1",headers={"abc"})
    public String test1(HttpServletResponse resp){
		System.out.println("aaa");
		System.out.println(request.getContentType());

		return "index";
    }
		
	@ModelAttribute("aaa") 
	public List<String> cityList() {  
	    return Arrays.asList("北京", "山东");  
	} 
	
	@RequestMapping(value="/test")  
    public String test2(@ModelAttribute("user")UserDO user,SessionStatus status){
		System.out.println(session.getAttribute("aaa"));
		return "index";
    }


}
