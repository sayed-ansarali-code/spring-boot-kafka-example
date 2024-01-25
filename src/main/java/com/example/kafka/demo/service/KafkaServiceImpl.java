package com.example.kafka.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Service
public class KafkaServiceImpl {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${spring.kafka.topic.name}")
    private String topicName;


    public void sendMessage(String message) throws ExecutionException, InterruptedException {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, 0, "grp", message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Success " + result.toString());
            }

            @Override
            public void onFailure(Throwable ex) {

                ex.printStackTrace();
            }
        });

        System.out.println(future.get());

    }


    @KafkaListener(topics = "${spring.kafka.topic.name}", containerFactory= "kafkaListenerContainerFactory")
//    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
