package edu.scut.tinymail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Usersetting;

/**
 * (Usersetting)表服务接口
 *
 * @author makejava
 * @since 2023-06-13 16:50:15
 */
public interface UsersettingService extends IService<Usersetting> {

        public ResponseResult<?> addUserSetting(Usersetting usersetting);

        public ResponseResult<?> getByName(String username);

}

