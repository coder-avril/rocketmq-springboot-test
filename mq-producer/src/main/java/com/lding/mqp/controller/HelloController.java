package com.lding.mqp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@RestController
public class HelloController {
    @Autowired
    private RocketMQTemplate template;

    @GetMapping("/send")
    public String sendMsg(String message) {
        // 同步消息
        SendResult result = template.syncSend("topic-springBoot-case1", message);
        return "发送消息成功: " + JSON.toJSONString(result);
    }

    @GetMapping("/asyncSend")
    public String asyncSend(String message) {
        // 异步消息
        template.asyncSend("topic-springBoot-case2", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult result) {
                System.out.println("异步发送成功: " + JSON.toJSONString(result));
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步发送失败: " + JSON.toJSONString(throwable.getMessage()));
            }
        });
        return "消息已经异步发送...";
    }

    @GetMapping("/oneWaySend")
    public String oneWaySend(String message) {
        // 一次性消息
        template.sendOneWay("topic-springBoot-case3", message);
        return "消息已经一次性发送...";
    }

    @GetMapping("/delaySend")
    public String delaySend(String message) throws Exception {
        // 方式1: 直接使用原生的api
        DefaultMQProducer producer = template.getProducer();
        Message msg = new Message("topic-springBoot-case4", message.getBytes(StandardCharsets.UTF_8));
        msg.setDelayTimeLevel(3);
        producer.send(msg);
        return "success!";
    }

    @GetMapping("/delaySend2")
    public String delaySend2(String message) throws Exception {
        // 方式2: 直接使用带有延迟级别的API
        org.springframework.messaging.Message<String> newMsg = MessageBuilder.withPayload(message).build();
        template.syncSend("topic-springBoot-case5", newMsg, 3000, 3);
        return "success!";
    }

    @GetMapping("/sendWithTag")
    public String sendWithTag(String message) {
        SendResult result = template.syncSend("topic-springBoot-case6" + ":" + "TagX", message);
        return "发送带有tag的消息成功: " + JSON.toJSONString(result);
    }

    @GetMapping("/sendWithKey")
    public String sendWithKey(String message) {
        org.springframework.messaging.Message<String> newMsg = MessageBuilder.withPayload(message)
                .setHeader(MessageConst.PROPERTY_KEYS, "key1").build();
        SendResult result = template.syncSend("topic-springBoot-case7", newMsg);
        return "发送带有key的消息成功: " + JSON.toJSONString(result);
    }

    @GetMapping("/sendWithProperties")
    public String sendWithProperties(String message) {
        HashMap<String, Object> properties = new HashMap<>();
        // 通过map来添加用户自定义属性（可以用于消费者的消息过滤
        properties.put("name", "Rose");
        template.convertAndSend("topic-springBoot-case8", message, properties);
        return "success!";
    }
}
