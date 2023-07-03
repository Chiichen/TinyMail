package edu.scut.tinymail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Userauth;
import jakarta.servlet.http.HttpServletRequest;

/**
 * (Userauth)表服务接口
 *
 * @author makejava
 * @since 2023-05-27 00:45:04
 */
public interface UserauthService extends IService<Userauth> {

    Userauth getByUsername(String username);

    ResponseResult<?> register(Userauth userauth, HttpServletRequest request);

    ResponseResult<?> setNickname(String username, String newNickname);

}

