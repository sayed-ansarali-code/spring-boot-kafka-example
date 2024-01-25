package com.example.kafka.demo.controller;

import com.example.kafka.demo.service.KafkaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class KafkaController {


    @Autowired
    private KafkaServiceImpl kafkaService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) throws ExecutionException, InterruptedException {
        System.out.println("message: " + message);
        kafkaService.sendMessage(message);
    }

}
