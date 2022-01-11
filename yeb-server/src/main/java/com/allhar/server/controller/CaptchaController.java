package com.allhar.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * CaptchaController
 *
 * @author allhar
 * @since 1.0.0
 **/
@RestController
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation("验证码")
    @GetMapping(value = "/captcha" , produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){

        // 生成验证码图片必要的设置
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader)
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");

        //---------------生成验证码开始 begin --------------
        //1 , 获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容:"+text);

        //2将验证码内容放入session
        request.getSession().setAttribute("captcha",text);

        //3根据文本验证码内容穿件图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            //输出流输出图片,格式JPG
            ImageIO.write(image,"jpg",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //---------------生成验证码结束 end --------------



    }

}
