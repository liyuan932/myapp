package com.mycompany.myapp.controller;

import com.mycompany.myapp.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by wb-liyuan.j on 2016/1/26.
 */
@Controller
public class HelloWorldController {
    @RequestMapping("/validate/hello")
    public Object validate(@Valid @ModelAttribute("user") UserModel user, Errors errors) {

        if (errors.hasErrors()) {
            return "errors";
        }
        return "success";
    }
}
