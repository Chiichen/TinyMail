package edu.scut.tinymail.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * (Usersetting)表实体类
 *
 * @author makejava
 * @since 2023-06-13 16:50:15
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("usersetting")
public class Usersetting {

    @TableField("settingid")

    private Long settingid;

    private String username;

    private String servername;

    private String serverusername;

    private String serverpassword;
    //0 for smtp, 1 for imap, 2 for sth else
    private Integer type;


    public Usersetting(String username, String servername, String serverusername, String serverpassword, Integer type) {
        this.username = username;
        this.servername = servername;
        this.serverusername = serverusername;
        this.serverpassword = serverpassword;
        this.type = type;
    }
}

