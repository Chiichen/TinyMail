package edu.scut.tinymail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.scut.tinymail.domain.entity.Usersetting;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * (Usersetting)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-13 16:50:15
 */
@Repository
public interface UsersettingMapper extends BaseMapper<Usersetting> {

    @Select("select * from usersetting where username=#{username}")
    List<Usersetting> getByName(String username);
}
