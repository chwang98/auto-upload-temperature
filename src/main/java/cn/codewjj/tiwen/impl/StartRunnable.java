package cn.codewjj.tiwen.impl;

import cn.codewjj.tiwen.controller.TiwenController;
import cn.codewjj.tiwen.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class StartRunnable implements Runnable {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy());
    @Override
    public void run() {
        log.info("获取用户所有信息");
        List<UserDO> userDOS = TiwenController.getUserMapper().selectAll();
        for (UserDO userDO : userDOS) {
            log.info(userDO.getUsername() + "开始打卡");
            threadPoolExecutor.submit(new TimeCallable(userDO));
        }
    }
}
