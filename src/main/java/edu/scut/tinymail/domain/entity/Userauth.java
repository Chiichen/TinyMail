package edu.scut.tinymail.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * (Userauth)表实体类
 *
 * @author makejava
 * @since 2023-05-27 00:45:02
 */
@SuppressWarnings("serial")
@Schema(description = "用户登录验证模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("userauth")
public class Userauth {

    @Schema(description = "登录注册的用户名")
    @TableId
    private String username;
    @Schema(description = "用户的昵称，登录后返回前端")
    private String nickname;
    @Schema(description = "登录注册的密码")
    private String password;
    @Schema(description = "登陆注册时输入的验证码")
    @TableField(exist = false)
    private String vc;
    @Schema(description = "登录后返回的用户权限数据")
    @TableField(exist = false)
    private List<String> authorities; // 后端返回给前端的用户权限数据

    public Userauth(String username, String nickname, String password, String vc) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNickname(nickname);
        this.vc = vc;
        this.setAuthorities(new ArrayList<>(Collections.singleton("user")));
    }

}

