package cn.zj.logistics.mapper;

import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderDetailExample;
import java.util.List;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long orderDetailId);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    List<OrderDetail> selectByExample(OrderDetailExample example);

    OrderDetail selectByPrimaryKey(Long orderDetailId);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}