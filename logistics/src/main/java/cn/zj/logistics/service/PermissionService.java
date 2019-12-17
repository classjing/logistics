package cn.zj.logistics.service;

import java.util.List;

import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;

public interface PermissionService {
	int deleteByPrimaryKey(Long permissionId);

	int insert(Permission record);

	List<Permission> selectByExample(PermissionExample example);

	Permission selectByPrimaryKey(Long permissionId);

	int updateByPrimaryKeySelective(Permission record);

	List<String> selectExpressionsByIds(List<Long> permissionIdsList);

}
