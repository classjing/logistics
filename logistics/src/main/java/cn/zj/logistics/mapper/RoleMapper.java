package cn.zj.logistics.mapper;

import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}