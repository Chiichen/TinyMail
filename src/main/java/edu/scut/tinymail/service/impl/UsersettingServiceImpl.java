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
            return new ResponseResult<>(403, "该用户不存在，请先注册");
            //先查询用户是不是已经有这条记录了
        else {
            QueryWrapper<Usersetting> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(
                    wrapper -> wrapper
                            .eq("username", usersetting.getUsername())
                            .eq("serverusername", usersetting.getServerusername())
                            .eq("servername", usersetting.getServername()));

            if (!usersettingMapper.selectList(queryWrapper).isEmpty())
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
        return new ResponseResult<>(200, "ok", usersettingMapper.getByName(username));
    }

    @Override
    public ResponseResult<?> setUserSetting(Usersetting usersetting) {
        if (userauthMapper.selectById(usersetting.getUsername()) == null)
            return new ResponseResult<>(403, "该用户不存在");
        else {
            UpdateWrapper<Usersetting> usersettingUpdateWrapper = new UpdateWrapper<>();
            usersettingUpdateWrapper.and(wrapper -> wrapper
                    .eq("username", usersetting.getUsername())
                    .eq("servername", usersetting.getServername())
                    .eq("serverusername", usersetting.getServerusername()));
            usersettingMapper.update(usersetting, usersettingUpdateWrapper);
            return new ResponseResult<>(200, "已成功更新" + usersetting.getUsername() + "的服务器配置");
        }


    }


    /**
     * @param serverusername
     * @return Result
     * @Description 只要删除就把所有邮箱都删了，包括IMAP和SMTP
     */
    @Override
    public ResponseResult<?> deleteSetting(String username, String serverusername) {
        QueryWrapper<Usersetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper
                .eq("username", username)
                .eq("serverusername", username));
        if (usersettingMapper.selectList(queryWrapper).isEmpty())
            return new ResponseResult<>(403, "该用户不存在这个服务器配置");
        else {
            usersettingMapper.delete(queryWrapper);
            return new ResponseResult<>(200, "ok");
        }
    }


    @Override
    public ResponseResult<List<Usersetting>> getSetting(String username, String serverusername) {
        QueryWrapper<Usersetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper
                .eq("username", username)
                .eq("serverusername", serverusername));
        if (usersettingMapper.selectList(queryWrapper).isEmpty())
            return new ResponseResult<>(403, "该用户不存在这个服务器配置");
        else
            return new ResponseResult<>(200, "ok", usersettingMapper.selectList(queryWrapper));
    }


    /**
     * @param serverusername
     * @return
     */
    @Override
    public ResponseResult<Usersetting> getSmtpSetting(String username, String serverusername) {
        if (getSetting(username, serverusername).getCode() != 200)
            return new ResponseResult<>(403, "该用户不存在这个服务器配置");
        else {
            for (Usersetting setting : getSetting(username, serverusername).getData()
            ) {
                if (setting.getType() == Usersetting.SMTP) return new ResponseResult<>(200, "ok", setting);
            }
        }

        return new ResponseResult<>(403, "该用户未配置SMTP服务器");
    }

    /**
     * @param serverusername
     * @return
     */
    @Override
    public ResponseResult<Usersetting> getImapSetting(String username, String serverusername) {
        if (getSetting(username, serverusername).getCode() != 200)
            return new ResponseResult<>(403, "该用户不存在这个服务器配置");
        else {
            for (Usersetting setting : getSetting(username, serverusername).getData()
            ) {
                if (setting.getType() == Usersetting.IMAP) return new ResponseResult<>(200, "ok", setting);
            }
        }
        return new ResponseResult<>(403, "该用户未配置IMAP服务器");
    }

}
