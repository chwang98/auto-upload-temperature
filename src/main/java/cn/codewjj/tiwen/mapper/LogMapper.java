package cn.codewjj.tiwen.mapper;

import cn.codewjj.tiwen.model.LogDO;
import cn.codewjj.tiwen.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


public interface LogMapper extends BaseMapper<LogDO> {
    @Select("SELECT log.`id`, `user`.username,log.tiwen,log.time,log.`status` FROM `user` INNER JOIN log ON `user`.id = log.user_id ORDER BY time DESC LIMIT 100")
    ArrayList<LogDO> selectAll();

}
