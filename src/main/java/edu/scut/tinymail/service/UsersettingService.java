package edu.scut.tinymail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Usersetting;

import java.util.List;

/**
 * (Usersetting)表服务接口
 *
 * @author makejava
 * @since 2023-06-13 16:50:15
 */
public interface UsersettingService extends IService<Usersetting> {

        ResponseResult<?> addUserSetting(Usersetting usersetting);

        ResponseResult<?> getByName(String username);

        ResponseResult<?> setUserSetting(Usersetting usersetting);

        /*
        只需要用户名和要获取的服务器信息就可以唯一获取一个服务器类型
         */
        ResponseResult<List<Usersetting>> getSetting(String serverusername);

        ResponseResult<?> deleteSetting(String serverusername);


        ResponseResult<Usersetting> getSmtpSetting(String serverusername);

        ResponseResult<Usersetting> getImapSetting(String serverusername);

}

