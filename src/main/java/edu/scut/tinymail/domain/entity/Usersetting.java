package edu.scut.tinymail.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @TableId("settingid")
    private Long settingid;

    private String username;

    private String servername;

    private String serverusername;

    private String serverpassword;

    /**
     * 0 for smtp, 1 for imap, 2 for sth else
     */
    private Integer type;


    public Usersetting(String username, String servername, String serverusername, String serverpassword, Integer type) {
        this.username = username;
        this.servername = servername;
        this.serverusername = serverusername;
        this.serverpassword = serverpassword;
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Usersetting)
                && ((Usersetting) object).getUsername().equals(username)
                && ((Usersetting) object).getServername().equals(servername)
                && ((Usersetting) object).getServerusername().equals(serverusername)
                && ((Usersetting) object).getServerpassword().equals(serverpassword)
                && ((Usersetting) object).getType().equals(type);
    }

    @Nullable
    public String getDomain() {
        String regex = "smtp|imap|mail(.*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.getServername());
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}

