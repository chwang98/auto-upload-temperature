package cn.codewjj.tiwen.mapper;

import cn.codewjj.tiwen.model.LogDO;
import cn.codewjj.tiwen.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;

public interface UserMapper extends BaseMapper<UserDO> {
    @Select("SELECT `user`.* FROM `user`")
    List<UserDO> selectAll();
}
