package com.hunter.seckill;

import com.hunter.seckill.service.KillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KillApplication.class)
public class KillApplicationTests {

    KillService killService;

    @Autowired
    public void setKillService(KillService killService) {
        this.killService = killService;
    }

    @Test
    public void contextLoads() {
        for (int i = 0; i < 50; i++) {
            String sale = killService.sale(1);
            System.out.println(sale);
        }
    }
}
