package com.yuunik.smsservice.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface SmsService {
    boolean sendSmsCode(String phoneNumber, Map<String, String> code) throws ExecutionException, InterruptedException;
}
