package com.edigest.journalApp.repository;

import com.edigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;
    @Test
    void testSendMail(){
        emailService.sendEmail("nitinkhandelwal5050@gmail.com", "Testing for java", "Hello");
    }
}
