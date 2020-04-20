package com.hunter.seckill.service.consumer;

import com.hunter.seckill.service.KillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Hunter
 * @date 2020/4/19
 */
@Component
@Slf4j
public class RabbitMqConsumer {

    KillService killService;

    @Autowired
    public void setKillService(KillService killService) {
        this.killService = killService;
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "sale"))
    public void consumer(String message){
        log.info("服务消费");
        killService.happySale(Integer.parseInt(message));
    }
}
