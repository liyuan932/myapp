package com.mycompany.myapp.controller;

import com.mycompany.myapp.model.FormatterModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by wb-liyuan.j on 2016/1/28.
 */
@Controller
public class FormatterController {

    @RequestMapping("/formatter/hello")
    public String formatter(@ModelAttribute("model")FormatterModel model) {
        model.setTotalCount(10000);
        model.setDiscount(0.51);
        model.setSumMoney(10000.13);
        model.setRegisterDate(new Date(2013-1900, 4, 1));
        model.setOrderDate(new Date(2013-1900, 4, 1, 20, 18, 18));

        return "formatter";
    }
}
