package com.testus.testus.service;

import java.util.UUID;

public interface EmailService {
    void sendSimpleMessage(String to, UUID uuid)throws Exception;
}
