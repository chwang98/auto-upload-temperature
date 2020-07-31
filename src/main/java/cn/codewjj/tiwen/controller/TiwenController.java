package cn.codewjj.tiwen.controller;

import cn.codewjj.tiwen.impl.TimeCallable;
import cn.codewjj.tiwen.mapper.LogMapper;
import cn.codewjj.tiwen.mapper.UserMapper;
import cn.codewjj.tiwen.model.LogDO;
import cn.codewjj.tiwen.model.UserDO;
import cn.codewjj.util.JSONResult;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(TiwenController.Path)
@Slf4j
@Data
public class TiwenController {
    public final static String Path = "/tiwen";

    public static Thread thread;

    @Autowired
    private UserMapper userMapper;

    //    @Autowired

    private static UserMapper userMapper2;
    public static UserMapper getUserMapper(){return userMapper2;}


    //    @Autowired
    private static LogMapper logMapper2;

    @Autowired
    public TiwenController(UserMapper um, LogMapper lm) {
        TiwenController.logMapper2 = lm;
        TiwenController.userMapper2 = um;
    }

//    static {
//        thread = new Thread(() -> {
//            threadPoolExecutor = new ThreadPoolExecutor(
//                    CORE_POOL_SIZE,
//                    MAX_POOL_SIZE,
//                    KEEP_ALIVE_TIME,
//                    TimeUnit.SECONDS,
//                    new ArrayBlockingQueue<>(QUEUE_CAPACITY),
//                    new ThreadPoolExecutor.CallerRunsPolicy());
//
//            log.info("获取用户所有信息");
//            List<UserDO> userDOS = userMapper2.selectAll();
//
//            for (UserDO userDO : userDOS) {
//                log.info(userDO.getUsername()+"开始打卡");
//                threadPoolExecutor.submit(new TimeCallable(userDO));
//            }
//        }, "time");
//    }


    @GetMapping
    public JSONResult<Object> getLog() {
        ArrayList<LogDO> logDOS = logMapper2.selectAll();
        return JSONResult.success(logDOS);
    }

    @PostMapping
    public JSONResult<Object> addUser(@RequestBody @Validated UserDO ud) {
        return JSONResult.failMsg("Server has error");
//        QueryWrapper<UserDO> userDOQueryWrapper = new QueryWrapper<>();
//        userDOQueryWrapper.eq("username", ud.getUsername());
//        List<UserDO> userDOS = userMapper.selectList(userDOQueryWrapper);
//        if (userDOS.size() > 0)
//        {
//            log.error(ud.toString()+"数据库中已存在该用户和密码");
//            return JSONResult.failMsg("数据库中已存在该用户和密码");
//        }
//        HashMap<String, Object> user = new HashMap<>();
//        String s = JSON.toJSONString(ud);
//        String result = HttpRequest.post("https://technology.cetcfuture.cn/usr/login")
//                .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
//                .body(s)
//                .timeout(10 * 1000)
//                .execute().body();
//        log.info("登陆返回："+result);
//        Map map = JSON.parseObject(result, Map.class);
//        log.info("用户信息："+map.get("data"));
//        try {
//            if (!(Boolean) map.get("data")) {
//                return JSONResult.failMsg("账号或密码错误");
//            }
//        } catch (ClassCastException e) {
////            e.printStackTrace();
//        }
//        ud.setId(UUID.randomUUID().toString());
//        int insert = userMapper.insert(ud);
//        if (insert > 0)
//            return JSONResult.successMsg("添加成功");
//        else
//            return JSONResult.failMsg("添加失败");
    }

    public static void addLog(UserDO ud,String tiwen, String status) {
        LogDO logDO = new LogDO();
        logDO.setId(UUID.randomUUID().toString());
        logDO.setTiwen(tiwen);
        logDO.setStatus(status);
        logDO.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString());
        logDO.setUser_id(ud.getId());
        logMapper2.insert(logDO);

    }
}
