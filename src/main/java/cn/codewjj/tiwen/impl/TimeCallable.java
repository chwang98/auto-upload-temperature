package cn.codewjj.tiwen.impl;

import cn.codewjj.tiwen.controller.TiwenController;
import cn.codewjj.tiwen.model.UserDO;
import cn.codewjj.util.JSONResult;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@Slf4j
public class TimeCallable implements Callable<Object> {

    UserDO ud;

    @Override
    public Object call() throws Exception {
        String tiwen = "";
        try {
            TimeUnit.SECONDS.sleep(RandomUtil.randomInt(0, 300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            try {
                String s = JSON.toJSONString(ud);
//                String result = HttpRequest.post("https://technology.cetcfuture.cn/usr/login")
//                        .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
//                        .body(s)
//                        .timeout(10 * 1000)
//                        .execute().body();
                String result = HttpRequest.post("https://education.cetcfuture.cn/usr/login")
                        .header(Header.CONTENT_TYPE, "application/json")
                        .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; Android 5.1.1; PRO 6 Plus Build/LMY48Z; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/52.0.2743.100 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand0")
                        .body(s)
                        .timeout(10 * 1000)
                        .execute().body();


                log.info("登陆返回：" + result);

                Map map = JSON.parseObject(result, Map.class);
                log.info("用户信息：" + map.get("data"));
                try {
                    if (!(Boolean) map.get("data")) {
                        log.error(ud.getUsername() + "账号或密码错误");
                        TiwenController.addLog(ud, tiwen, "失败" + "账号或密码错误");
                        return JSONResult.failMsg("账号或密码错误");
                    }
                } catch (ClassCastException e) {
//                e.printStackTrace();
                }
                Map data = JSON.parseObject(String.valueOf(map.get("data")), Map.class);
                String token = data.get("token").toString();
                log.info("token获取" + token);
                tiwen = "36." + RandomUtil.randomInt(3, 7);
//                String body = HttpRequest.post("https://technology.cetcfuture.cn/tblDailyReport/addTblDailyReport")
//                        .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
//                        .header("x-user-token", token)
//                        .header(Header.USER_AGENT,"")
//                        .body("{\n" +
//                                "\t\"tblDailyReportTempValue\": "+ tiwen +",\n" +
//                                "\t\"tblDailyReportWorkStatus\": 1,\n" +
//                                "\t\"tblActivityTrack\": [],\n" +
//                                "\t\"question1\": 0,\n" +
//                                "\t\"question2\": 0,\n" +
//                                "\t\"question3\": 0,\n" +
//                                "\t\"question4\": 0,\n" +
//                                "\t\"question5\": 0,\n" +
//                                "\t\"question6\": 0,\n" +
//                                "\t\"question7\": 0,\n" +
//                                "\t\"question8\": 0,\n" +
//                                "\t\"reason\": \"\",\n" +
//                                "\t\"currentLocation\": {\n" +
//                                "\t\t\"nationId\": \"\",\n" +
//                                "\t\t\"nation\": \"\",\n" +
//                                "\t\t\"province\": \"\",\n" +
//                                "\t\t\"city\": \"\",\n" +
//                                "\t\t\"county\": \"\"\n" +
//                                "\t}\n" +
//                                "}").execute().body();

                String body = HttpRequest.post("https://education.cetcfuture.cn/tblDailyReport/addTblDailyReport")
                        .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                        .header("x-user-token", token)
                        .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; Android 5.1.1; PRO 6 Plus Build/LMY48Z; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/52.0.2743.100 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand0")
                        .body(
                                "{\n" +
                                        "    \"tblDailyReportTempValue\": "+tiwen+",\n" +
                                        "    \"tblDailyReportWorkStatus\": 1,\n" +
                                        "    \"tblActivityTrack\": [],\n" +
                                        "    \"question1\": 0,\n" +
                                        "    \"question2\": 0,\n" +
                                        "    \"question3\": 0,\n" +
                                        "    \"question4\": 0,\n" +
                                        "    \"question5\": 0,\n" +
                                        "    \"question6\": 0,\n" +
                                        "    \"question7\": 0,\n" +
                                        "    \"question8\": 0,\n" +
                                        "    \"question10\": 0,\n" +
                                        "    \"reason\": \"\",\n" +
                                        "    \"currentLocation\": {\n" +
                                        "        \"nationId\": \"\",\n" +
                                        "        \"nation\": \"\",\n" +
                                        "        \"province\": \"\",\n" +
                                        "        \"city\": \"\",\n" +
                                        "        \"county\": \"\"\n" +
                                        "    }\n" +
                                        "}"
                        ).execute().body();


                try {
                    if ("1".equals(body)) {
                        log.info(ud.getUsername() + "打卡成功===" + tiwen);
                        TiwenController.addLog(ud, tiwen, "打卡成功--" + body);
                        break;
                    }else{
                        log.info(ud.getUsername() + "打卡失败===" + body);
                        TiwenController.addLog(ud, tiwen, "服务器错误--" + body);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            } catch (Exception e) {
                try {
                    log.error(ud.getUsername() + "打卡失败 5分钟后重试");
                    TiwenController.addLog(ud, tiwen, "失败" + e.toString());
                    TimeUnit.MINUTES.sleep(5);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return null;
    }

}
