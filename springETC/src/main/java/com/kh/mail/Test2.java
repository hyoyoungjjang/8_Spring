package com.kh.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class Test2 {
	
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("send")
	public String mail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("이메일 전송 테스트2");
		message.setText("이메일을 테스트 하도록 하겠스니다.");
		
		String[] to = {"khy20030930@gmail.com"};
		message.setTo(to);
		
		String[] cc = {"khy20030930@gmail.com"};
		message.setCc(cc);
		
		sender.send(message);
		
		return "redirect:/";
	}
	
	@GetMapping("hyperemail")
	public String hyperMail() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		
		String[] to = {"khy20030930@gmail.com"};
		helper.setTo(to);
		
		String[] cc = {"khy20030930@gmail.com"};
		helper.setCc(cc);
		
		helper.setSubject("이메일 전송테스트3");
		
		String url = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.port(8899).path("/user")
				.queryParam("user_id", "khy20030930")
				.queryParam("uuid", "asdf")
				.toUriString();	
		
		helper.setText("<a href='"+ url +"'> 웹사이트로 이동 </a>", true);
		sender.send(message);
		
		return "redirect:/";
	}
	
	@GetMapping("sendfile")
	public String sendFile() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		
		String[] to = {"khy20030930@gmail.com"};
		helper.setTo(to);
		
		String[] cc = {"khy20030930@gmail.com"};
		helper.setCc(cc);
		
		helper.setSubject("이미지 전송 이메일 테스트");
		
		helper.setText("파일 전송 테스트입니다");
		
		//첨부파일 추가
		DataSource source = new FileDataSource("C:\\Users\\user1\\Downloads\\heart.png");
		helper.addAttachment(source.getName(), source);
	
		sender.send(message);
		
		return "redirect:/";
	}
}
