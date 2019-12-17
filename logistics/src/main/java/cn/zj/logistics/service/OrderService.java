package cn.zj.logistics.service;

import java.util.List;

import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderExample;

public interface OrderService {
	int deleteByPrimaryKey(Long orderId);

	int insert(Order record);

	List<Order> selectByExample(OrderExample example);

	Order selectByPrimaryKey(Long orderId);

	int updateByPrimaryKeySelective(Order record);

	List<OrderDetail> selectOrderDetailsByOrderId(Long orderId);


}
