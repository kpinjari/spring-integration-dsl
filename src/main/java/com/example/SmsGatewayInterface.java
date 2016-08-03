package com.example;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

/**
 * Created by szymon on 02.08.16.
 */
@MessagingGateway
@Component
public interface SmsGatewayInterface {

    @Gateway(requestChannel = "incomingObject.input")
    boolean sendSms(IncomingObject incomingObject);
}
