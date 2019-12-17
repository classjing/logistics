package cn.zj.logistics.service;

import java.util.List;

import cn.zj.logistics.pojo.BaseData;
import cn.zj.logistics.pojo.BaseDataExample;

public interface BaseDataService {
	int deleteByPrimaryKey(Long baseId);

	int insert(BaseData record);

	List<BaseData> selectByExample(BaseDataExample example);

	BaseData selectByPrimaryKey(Long baseId);

	int updateByPrimaryKeySelective(BaseData record);

	List<BaseData> selectBaseDataByParentName(String string);

}
