# seckill
SpringBoot 秒杀

- 悲观锁：同步锁实现方式 加 synchronized 关键字代码块
- 乐观锁：基于数据库层面，加入版本号；秒杀入口加入令牌桶限流
- 同步改为异步，消息队列：基于RabbitMQ消息队列实现
