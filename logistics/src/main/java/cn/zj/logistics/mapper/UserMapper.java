package cn.zj.logistics.mapper;

import java.util.List;

import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	
}