package com.grglucastr.homeincapi.service.v2;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

}
