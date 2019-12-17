package cn.zj.logistics.service;

import java.util.List;

import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;

public interface CustomerService {
	int deleteByPrimaryKey(Long customerId);

	int insert(Customer record);

	List<Customer> selectByExample(CustomerExample example);

	Customer selectByPrimaryKey(Long customerId);

	int updateByPrimaryKeySelective(Customer record);


}
