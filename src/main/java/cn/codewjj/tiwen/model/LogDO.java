package cn.codewjj.tiwen.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@TableName("log")
public class LogDO implements Serializable {

    @TableField(exist = false)
    private String username;

    private String id;
    private String tiwen;
    private String time;
    private String status;
    private String user_id;
}
