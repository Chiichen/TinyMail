package edu.scut.tinymail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Usersetting;
import edu.scut.tinymail.mapper.UserauthMapper;
import edu.scut.tinymail.mapper.UsersettingMapper;
import edu.scut.tinymail.service.UsersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        else {
            if (getByName(usersetting.getUsername()).getData().contains(usersetting))
                return new ResponseResult<>(403, "这个邮箱已经添加过了");
            else {
                //Todo 检验用户邮箱配置是否可用
                usersettingMapper.insert(usersetting);


                return new ResponseResult<>(200, "成功添加用户邮箱配置");
            }

        }

    }

    /**
     * @param username
     * @return
     */
    @Override
    public ResponseResult<List<Usersetting>> getByName(String username) {
        if (userauthMapper.selectById(username) == null)
            return new ResponseResult<>(403, "该用户不存在");
        return new ResponseResult<List<Usersetting>>(200, "ok", usersettingMapper.getByName(username));

    }

    @Override
    public ResponseResult<?> setUserSetting(Usersetting usersetting) {
        if (userauthMapper.selectById(usersetting.getUsername()) == null)
            return new ResponseResult<>(403, "该用户不存在");
        if (usersettingMapper.getByName(usersetting.getUsername()).contains(usersetting))
            return new ResponseResult<>(403, "这个配置已经存在了");

        else {
            UpdateWrapper<Usersetting> usersettingUpdateWrapper = new UpdateWrapper<>();
            usersettingUpdateWrapper.and(wrapper -> wrapper.eq("username", usersetting.getUsername()).eq("servername", usersetting.getServername()));
            usersettingMapper.update(usersetting, usersettingUpdateWrapper);
            return new ResponseResult<>(200, "已成功更新" + usersetting.getUsername() + "的服务器配置");
        }


    }


    /**
     * @param username
     * @param server
     * @return
     */
    @Override
    public ResponseResult<?> deleteSetting(String username, String server) {
        if (usersettingMapper.getByName(username) == null) return new ResponseResult<>(403, "该用户不存在配置");
        else {
            if (getSetting(username, server) == null) return new ResponseResult<>(403, "该用户不存在这个服务器配置");
            else {
                QueryWrapper<Usersetting> deleteWrapper = new QueryWrapper<>();
                deleteWrapper.and(wrapper -> wrapper.eq("username", username).eq("servername", server));
                usersettingMapper.delete(deleteWrapper);
                return new ResponseResult<>(200, "ok");
            }
        }

    }


    @Override
    public Usersetting getSetting(String username, String server) {
        QueryWrapper<Usersetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("username", username).eq("servername", server));
        return usersettingMapper.selectOne(queryWrapper);
    }

}
