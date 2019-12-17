package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.BaseDataMapper;
import cn.zj.logistics.pojo.BaseData;
import cn.zj.logistics.pojo.BaseDataExample;
import cn.zj.logistics.service.BaseDataService;

@Service
public class BaseDataServiceImpl implements BaseDataService {
	
	@Autowired
	private BaseDataMapper baseDataMapper;
	
	@Override
	public int deleteByPrimaryKey(Long baseId) {
		return baseDataMapper.deleteByPrimaryKey(baseId);
	}

	@Override
	public int insert(BaseData record) {
		return baseDataMapper.insert(record);
	}

	@Override
	public List<BaseData> selectByExample(BaseDataExample example) {
		return baseDataMapper.selectByExample(example);
	}

	@Override
	public BaseData selectByPrimaryKey(Long baseId) {
		return baseDataMapper.selectByPrimaryKey(baseId);
	}

	@Override
	public int updateByPrimaryKeySelective(BaseData record) {
		return baseDataMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<BaseData> selectBaseDataByParentName(String parentName) {
		return baseDataMapper.selectBaseDataByParentName(parentName);
	}

}
