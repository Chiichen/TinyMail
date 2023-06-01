package edu.scut.tinymail.domain.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userauth {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    @TableField(exist = false)
    private List<String> authorities;

    public Userauth(String username, String nickname, String password) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNickname(nickname);
        this.setAuthorities(new ArrayList<>(Collections.singleton("user")));
        this.setId(null);
    }
}

