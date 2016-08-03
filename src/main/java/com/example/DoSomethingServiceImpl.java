package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by szymon on 02.08.16.
 */
@Service
public class DoSomethingServiceImpl implements DoSomethingService {

    @Autowired
    SmsGatewayInterface smsGatewayInterface;

    @Override
    public void doSomething(IncomingObject incomingObject) {
        System.out.println(incomingObject.getClientClass().getName());
        boolean x = smsGatewayInterface.sendSms(incomingObject);
        System.out.println("ROBIE COS : " + x);
    }
}
