package com.mycompany.myapp.controller;

import com.mycompany.myapp.model.PhoneNumberModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wb-liyuan.j on 2016/1/28.
 */
@Controller
public class ConverterController {

    @RequestMapping("/converter/hello")
    @ResponseBody
    public Object converter(@ModelAttribute("phone") PhoneNumberModel phone) {
        return phone;
    }
}
