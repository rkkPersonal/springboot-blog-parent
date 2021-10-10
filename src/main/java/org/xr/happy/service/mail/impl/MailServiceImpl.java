package org.xr.happy.service.mail.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xr.happy.service.mail.MailService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private SpringTemplateEngine springTemplateEngine;

    @Override
    public void postTemplateEmail() throws MessagingException {

        String path="email/mail.html";



        String from="17635841699@163.com";
        String [] mailTo=new String[]{from,"909563510@qq.com"};


        List<MimeMessage> mimeMessageList=new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            Map<String, Object> variables= new HashMap<>();
            variables.put("username","steven"+i);
            variables.put("age",18+i);

            Context context = new Context();

            context.setVariables(variables);

            String content = springTemplateEngine.process(path, context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(content,true);
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setCc(new String[]{"871760440@qq.com",from});
            mimeMessageHelper.setSubject("Pictures"+i);

            mimeMessageList.add(mimeMessage);
        }




        /*FileSystemResource resource = new FileSystemResource(new File("D:\\dev\\JavaStudy\\springboot-blog-parent\\src\\main\\resources\\love.jpg"));

        mimeMessageHelper.addAttachment("love.jpg",resource);*/
        javaMailSender.send(mimeMessageList.toArray(new MimeMessage[mimeMessageList.size()]));

    }
}
