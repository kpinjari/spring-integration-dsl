package com.example;

import org.springframework.stereotype.Service;

/**
 * Created by szymon on 02.08.16.
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public boolean send() {
        return true;
    }
}
