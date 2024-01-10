package com.kafka.demo.controller;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.demo.configuration.KafkaTopicConfig;
import com.kafka.demo.configuration.MyTopicConsumer;

import java.util.List;

@RestController
public class KafkaController {

    private KafkaTemplate<String, String> template;
    private MyTopicConsumer myTopicConsumer;
    private KafkaTopicConfig config;
    
    public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer, KafkaTopicConfig config) {
        this.template = template;
        this.myTopicConsumer = myTopicConsumer;
        this.config = config;
    }

    @GetMapping("/kafka/produce")
    public void produce(@RequestParam String message) {
        template.send("myTopic", message);
    }
    
    @GetMapping("/kafka/messages")
        public List<String> getMessages() {
            return myTopicConsumer.getMessages();
        }
    
    @GetMapping("/kafka/defaultMessages")
    public String getDefaultMessages() {
		if(myTopicConsumer.getMessages().size() == 0) {
			return config.myTopic().toString();
		}
		else
		return null;
    }

}