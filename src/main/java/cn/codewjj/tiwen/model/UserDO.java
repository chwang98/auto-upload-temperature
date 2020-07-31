package cn.codewjj.tiwen.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 4407173831732808407L;
    private String id;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;

}
