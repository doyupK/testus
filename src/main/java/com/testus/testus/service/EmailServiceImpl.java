package com.testus.testus.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService{
 
    @Autowired
    JavaMailSender emailSender;
 
//    public static final String ePw = createKey();
 
    private MimeMessage createResetMessage(String to, UUID uuid)throws Exception{
        System.out.println("보내는 대상 : "+ to);
//        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();
 
        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("[TESTUS] 비밀번호 초기화 메일");//제목
 
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 TESTUS입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<h4>비밀번호 재설정 링크로 비밀번호를 변경해주세요.</h4>";
        msgg+= "<br>";
        msgg+= "<p>안녕하세요.<p>";
        msgg+= "<p>비밀번호 재설정을 위해 아래 버튼을 클릭해주세요.<p>";
        msgg+= "<br>";
        msgg+= "<p>만일 비밀번호 찾기를 요청하지 않았는데 이 메일을 받으신 경우,<p>";
        msgg+= "<p>보안 상태(비밀번호 등)를 다시 한번 확인해주세요<p>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>비밀번호 재설정 인증 링크입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= uuid+"</strong><div><br/> ";
        msgg+= "</div>";
        msgg+= "</div>";
        msgg+= "</div>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<p>테스트어스 드림.<p>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("testusofficial@gmail.com","TESTUS"));//보내는 사람
 
        return message;
    }
 
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
 
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
 
            switch (index) {
                case 0:
                    key.append((char) (rnd.nextInt(26) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) (rnd.nextInt(26) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
    @Override
    public void sendSimpleMessage(String to, UUID uuid)throws Exception {
        MimeMessage message = createResetMessage(to, uuid);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}