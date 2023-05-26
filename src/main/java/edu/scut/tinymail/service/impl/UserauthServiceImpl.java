package edu.scut.tinymail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scut.tinymail.config.RedisConfig;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entry.Userauth;
import edu.scut.tinymail.domain.vo.UserauthVO;
import edu.scut.tinymail.mapper.UserauthMapper;
import edu.scut.tinymail.service.UserauthService;
import edu.scut.tinymail.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

/**
 * (Userauth)表服务实现类
 *
 * @author makejava
 * @since 2023-05-27 00:45:06
 */
@Service("userauthService")
public class UserauthServiceImpl extends ServiceImpl<UserauthMapper, Userauth> implements UserauthService {

    @Autowired
     private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult<?> login(UserauthVO userauthVO) {
        return null;
    }


}
