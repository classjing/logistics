package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.OrderDetailMapper;
import cn.zj.logistics.mapper.OrderMapper;
import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderDetailExample;
import cn.zj.logistics.pojo.OrderDetailExample.Criteria;
import cn.zj.logistics.pojo.OrderExample;
import cn.zj.logistics.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	
	@Override
	public int deleteByPrimaryKey(Long orderId) {
		return orderMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public int insert(Order order) {
		
		/*
		 * 下单业务功能
		 * 1，保存订单基本信息
		 * 2，保存订单的orderId 设置订单明细对象
		 * 3，保存订单明细对象
		 * 
		 */
		System.out.println("保存前 :"+order);
		int row = orderMapper.insert(order);
		System.out.println("保存后 :"+order);
		
		List<OrderDetail> orderDetails = order.getOrderDetails();
		for (OrderDetail orderDetail : orderDetails) {
			//将数据库生成的orderId设置订单明细
			orderDetail.setOrderId(order.getOrderId());
			System.out.println(orderDetail);
			//保存订单明细
			orderDetailMapper.insert(orderDetail);
		}
		
		return row;
	}

	@Override
	public List<Order> selectByExample(OrderExample example) {
		
		
		
		
		return orderMapper.selectByExample(example);
	}

	@Override
	public Order selectByPrimaryKey(Long orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<OrderDetail> selectOrderDetailsByOrderId(Long orderId) {
		OrderDetailExample example = new OrderDetailExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(example);
		return orderDetails;
	}


}
