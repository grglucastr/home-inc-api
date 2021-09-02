package com.grglucastr.homeincapi.service.v2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTests {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    public void testSendEmail(){

        final String to = "to";
        final String subject = "subject";
        final String text = "text";

        doNothing().when(emailSender).send(isA(SimpleMailMessage.class));

        emailService.sendSimpleMessage(to, subject, text);
    }

}
