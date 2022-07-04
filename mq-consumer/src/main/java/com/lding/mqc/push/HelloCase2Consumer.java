package com.lding.mqc.push;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic-springBoot-case2",
        messageModel = MessageModel.CLUSTERING, // 指定集群模式（默认也是这个模式
        consumerGroup = "sbTest-consumer-group") // 其实为了不影响测试 消费者组最好也设置成不一样 防止有影响
public class HelloCase2Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("接收到异步消息: " + message);
    }
}
