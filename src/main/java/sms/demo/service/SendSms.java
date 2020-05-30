package sms.demo.service;

import java.util.Map;

public interface SendSms {
    public boolean send(String phoneNumbers, String templateCode, Map<String,Object> code);
}
