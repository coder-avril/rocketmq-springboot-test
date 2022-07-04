package com.lding.mqc.push;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic-springBoot-case8",
        selectorType = SelectorType.SQL92,
        selectorExpression = "name='Rose'",
        consumerGroup = "sbTest-consumer-group2")
public class HelloCase4Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("接收到过滤后消息: " + message);
    }
}
