package edu.scut.tinymail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Userauth;
import edu.scut.tinymail.mapper.UserauthMapper;
import edu.scut.tinymail.service.UserauthService;
import edu.scut.tinymail.utils.RedisCache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static edu.scut.tinymail.controller.VerifyCodeController.VERIFY_CODE_KEY;


/**
 * (Userauth)表服务实现类
 *
 * @author makejava
 * @since 2023-05-27 00:45:06
 */
@Service("userauthService")
public class UserauthServiceImpl extends ServiceImpl<UserauthMapper, Userauth> implements UserauthService {


    @Autowired
    private RedisCache redisCache;

    @Override
    public Userauth getByUsername(String username) {
        QueryWrapper<Userauth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        return getBaseMapper().selectOne(queryWrapper);
    }

    @Override
    public ResponseResult<?> register(Userauth userauth, HttpServletRequest request) {
        if (!userauth.getVc().equals(request.getSession().getAttribute(VERIFY_CODE_KEY)))
            return new ResponseResult<>(401, "验证码错误");

        UserauthMapper userauthMapper = getBaseMapper();
        QueryWrapper<Userauth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userauth.getUsername());

        if (userauthMapper.selectOne(queryWrapper) != null) return new ResponseResult<>(401, "用户名已存在");
        else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userauthMapper.insert(new Userauth(null,
                    userauth.getUsername(),
                    userauth.getNickname(),
                    bCryptPasswordEncoder.encode(userauth.getPassword()),
                    Collections.singletonList("user")));
            return new ResponseResult<>(200, "成功注册");
        }

    }


}



