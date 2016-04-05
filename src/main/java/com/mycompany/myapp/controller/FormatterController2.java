package com.mycompany.myapp.controller;

import com.mycompany.myapp.model.FormatterModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by wb-liyuan.j on 2016/1/28.
 */
@Controller
public class FormatterController2 {

    @RequestMapping("/formatter/hello2")
    public String formatter(@DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam("date") Date date) {
        System.out.println(date);
        return "success";
    }
}
