package cn.zj.logistics.mapper;

import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long customerId);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);

    Customer selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}