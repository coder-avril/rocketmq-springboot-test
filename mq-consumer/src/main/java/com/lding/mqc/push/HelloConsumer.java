package com.lding.mqc.push;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic-springBoot-case1", consumerGroup = "sbTest-consumer-group")
public class HelloConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("接收到消息: " + message);
    }
}
