package edu.scut.tinymail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Usersetting;
import edu.scut.tinymail.mapper.UserauthMapper;
import edu.scut.tinymail.mapper.UsersettingMapper;
import edu.scut.tinymail.service.UsersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Usersetting)表服务实现类
 *
 * @author makejava
 * @since 2023-06-13 16:50:15
 */
@Service("usersettingService")
public class UsersettingServiceImpl extends ServiceImpl<UsersettingMapper, Usersetting> implements UsersettingService {

    @Autowired
    UserauthMapper userauthMapper;

    @Autowired
    UsersettingMapper usersettingMapper;

    @Override
    public ResponseResult<?> addUserSetting(Usersetting usersetting) {
        if (userauthMapper.selectById(usersetting.getUsername()) == null)
            return new ResponseResult<>(403, "该用户不存在");
        //先查询用户是不是已经有这条记录了

        usersettingMapper.insert(usersetting);
        return null;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public ResponseResult<?> getByName(String username) {
        if (userauthMapper.selectById(username) == null)
            return null;

        return null;

    }


}
