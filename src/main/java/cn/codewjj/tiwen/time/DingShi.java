package cn.codewjj.tiwen.time;

import cn.codewjj.tiwen.controller.TiwenController;
import cn.codewjj.tiwen.impl.StartRunnable;
import cn.codewjj.tiwen.impl.TimeCallable;
import cn.codewjj.tiwen.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;


//@Component
@Service
@EnableScheduling
@Slf4j
public class DingShi {

    @Scheduled(cron = "12 10 08 * * ?")
    private void dingshi(){
        log.info("定时任务开始");
        new Thread(new StartRunnable(),"time").start();
        log.info("定时任务结束");
    }
}
