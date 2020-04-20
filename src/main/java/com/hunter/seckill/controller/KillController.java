package com.hunter.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.hunter.seckill.service.KillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Hunter
 * @date 2020/4/19
 */
@RestController
@Slf4j
public class KillController {

    KillService killService;

    RabbitTemplate rabbitTemplate;

    //一秒钟最多处理10个并发请求
    RateLimiter rateLimiter = RateLimiter.create(10);


    public String rateLimiterTest(){
        //一秒钟最多处理10个并发请求
        RateLimiter rateLimiter = RateLimiter.create(10);

        //响应时间
        rateLimiter.acquire();

        //do something
        return "ok";
    }

    @Autowired
    public void setKillService(KillService killService) {
        this.killService = killService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 悲观锁
     * @param id id
     * @return string
     */
    @GetMapping("/sale/{id}")
    public String sale(@PathVariable int id){
        //悲观锁
        synchronized (this){
            return killService.sale(id);
        }
    }

    /**
     * 乐观锁
     * @param id id
     * @return string
     */
    @GetMapping("/happySale/{id}")
    public String happySale(@PathVariable int id){
        if (!rateLimiter.tryAcquire(1, TimeUnit.SECONDS)){
            return "该商品过于火爆，请重试";
        }
        return killService.happySale(id);
    }

    /**
     * 消息队列
     * @param id id
     * @return success
     */
    @GetMapping("/rabbitmq/sale/{id}")
    public String mqSale(@PathVariable int id){
        rabbitTemplate.convertAndSend("sale",id);
        return "success";
    }
}
