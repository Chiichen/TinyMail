package edu.scut.tinymail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entry.Userauth;
import edu.scut.tinymail.domain.vo.UserauthVO;

/**
 * (Userauth)表服务接口
 *
 * @author makejava
 * @since 2023-05-27 00:45:04
 */
public interface UserauthService extends IService<Userauth> {
    ResponseResult<?> login(UserauthVO userauthVO);
}

