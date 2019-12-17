package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.PermissionMapper;
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public int deleteByPrimaryKey(Long permissionId) {
		return permissionMapper.deleteByPrimaryKey(permissionId);
	}

	@Override
	public int insert(Permission record) {
		return permissionMapper.insert(record);
	}

	@Override
	public List<Permission> selectByExample(PermissionExample example) {
		return permissionMapper.selectByExample(example);
	}

	@Override
	public Permission selectByPrimaryKey(Long permissionId) {
		return permissionMapper.selectByPrimaryKey(permissionId);
	}

	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		return permissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<String> selectExpressionsByIds(List<Long> permissionIdsList) {
		return permissionMapper.selectExpressionsByIds(permissionIdsList);
	}

}
