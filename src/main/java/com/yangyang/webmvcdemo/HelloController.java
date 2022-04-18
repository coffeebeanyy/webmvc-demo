package com.yangyang.webmvcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController implements EmbeddedValueResolverAware {
    private StringValueResolver stringValueResolver;

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }

    @GetMapping({"/world","/world2"})
    public String hello(){
        return "hello world";
    }
    @GetMapping("/wx")
    public void tokenVerify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        boolean isVerify = SignUtil.checkSignature(signature, timestamp, nonce);

//        if (!isVerify) {
//            log.error("消息不是来自微信服务器！");
//        } else {
//            response.getOutputStream().println(echostr);
//        }
        response.getOutputStream().println(echostr);
    }

}
