package com.mycompany.myapp.controller.demo;

import com.mycompany.myapp.controller.BaseController;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.utils.excel.ExcelUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping({"/","/index.do"})
    public String index(){
        return "demo/index";
    }


    @RequestMapping("/view1.do")
    public ModelAndView view1(UserQuery query){
        ModelAndView mv = new ModelAndView("demo/view");
        mv.addObject("users",userService.queryUser(query));
        return mv;
    }

    @RequestMapping("/view2.do")
    public String view2(UserQuery query,Model model, ModelMap mm,Map<String,Object> map){
        model.addAttribute("users",userService.queryUser(query));
        System.out.println((model == mm) + "," + (model == map));  //true,true
        return "demo/view";
    }

    @RequestMapping("/stream.do")
    public ResponseEntity<byte[]> stream(UserQuery query) throws Exception {

        File file = ExcelUtils.generatorFile(userService.queryUser(query));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
    }

    @RequestMapping("/image.do")
    public ResponseEntity<byte[]> image() throws Exception {

        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 36));
        g2d.drawString("Hi there, how are you doing today?", 5, g2d.getFontMetrics().getHeight());
        g2d.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", out);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(out.toByteArray(),headers, HttpStatus.CREATED);
    }
}