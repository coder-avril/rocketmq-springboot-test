package com.lding.mqc.push;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic-springBoot-case3",
        messageModel = MessageModel.BROADCASTING, // 指定广播模式
        consumerGroup = "sbTest-consumer-group")
public class HelloCase3Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("接收到一次性消息: " + message);
    }
}
