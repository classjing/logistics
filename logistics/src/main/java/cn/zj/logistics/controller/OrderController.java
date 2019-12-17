package cn.zj.logistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.BaseData;
import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderExample;
import cn.zj.logistics.pojo.OrderExample.Criteria;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.BaseDataService;
import cn.zj.logistics.service.CustomerService;
import cn.zj.logistics.service.OrderService;
import cn.zj.logistics.service.UserService;
import cn.zj.logistics.utils.Constant;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private BaseDataService baseDataService;

	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/orderPage")
	public String orderPage() {
		
		
		return "orderPage";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Order> list(@RequestParam(defaultValue = "1")Integer pageNum,
							   @RequestParam(defaultValue = "10")Integer pageSize,
							   String keyword){
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		OrderExample example = new OrderExample();
		
		//条件搜索
		if(StringUtils.isNotBlank(keyword)) {
			
			Criteria criteria1 = example.createCriteria();
			criteria1.andShippingNameLike("%"+keyword+"%");
		}
		
		List<Order> orders = orderService.selectByExample(example );
		
		PageInfo<Order> pageInfo = new PageInfo<Order>(orders);
		
		return pageInfo;
	}
	
	
	//获取所有的权限数据，以供角色分配权限使用
	@RequestMapping("/selectAllOrder")
	@ResponseBody
	public List<Order> selectAllOrder(){
		
		
		OrderExample example = new OrderExample();
		List<Order> orders = orderService.selectByExample(example );
		
		return orders;
	}
	
	
	
	//编辑功能，新增和修改通用
	@RequestMapping("/edit")
	public String edit(Model m,Long orderId) {
		
		
		//修改操作，根据orderId查询出Order对象并共享给orderEdit.jsp 进行数据回显
		if(orderId !=null) {
			Order order = orderService.selectByPrimaryKey(orderId);
			m.addAttribute("order", order);
		}
		
		
		/*
		 * 后台查询共享给前台页面数据
		 * 
		 * 1，业务员
		 * 2，客户
		 * 3，区间管理
		 * 4，付款方式
		 * 5，货运方式
		 * 6,   取件方式
		 * 7，单位
		 * 
		 */
		//1，业务员
		UserExample example = new UserExample();
		cn.zj.logistics.pojo.UserExample.Criteria criteria = example.createCriteria();
		criteria.andRolenameEqualTo(Constant.ROLE_SALESMAN);
		List<User> users = userService.selectByExample(example);
		m.addAttribute("users", users);
		
		//2，客户
		CustomerExample customerExample = new CustomerExample();
		List<Customer> customers = customerService.selectByExample(customerExample);
		m.addAttribute("customers", customers);
		//3，区间管理
		List<BaseData> intervals = baseDataService.selectBaseDataByParentName(Constant.BASIC_COMMON_INTERVAL);
		m.addAttribute("intervals", intervals);
		//4，付款方式
		List<BaseData> payments = baseDataService.selectBaseDataByParentName(Constant.BASIC_PAYMENT_TYPE);
		m.addAttribute("payments", payments);
		//5，货运方式
		List<BaseData> freights = baseDataService.selectBaseDataByParentName(Constant.BASIC_FREIGHT_TYPE);
		m.addAttribute("freights", freights);
		//6，取件方式
		List<BaseData> fetchTypes = baseDataService.selectBaseDataByParentName(Constant.BASIC_FETCH_TYPE);
		m.addAttribute("fetchTypes", fetchTypes);
		//7，单位
		List<BaseData> units = baseDataService.selectBaseDataByParentName(Constant.BASIC_UNIT);
		m.addAttribute("units", units);
		
		
		return "orderEdit";
	}
	
	
	//修改功能
	@RequestMapping("/update")
	@ResponseBody
	public MessageObject update(Order order) {
		
		
		MessageObject mo = MessageObject.createMo(0, "修改数据失败，请联系管理员");
		
		int row = orderService.updateByPrimaryKeySelective(order);
		if(row == 1) {
			mo = MessageObject.createMo(1, "修改数据成功");
		}
		return mo;
	}
	
	
	@RequestMapping("/detail")
	@ResponseBody
	public List<OrderDetail> detail(Long orderId){
		List<OrderDetail> orderDetails = orderService.selectOrderDetailsByOrderId(orderId);
		return orderDetails;
	}
	
	
	
	//新增功能
	
	@RequestMapping("/insert")
	@ResponseBody
	public MessageObject insert(@RequestBody Order order) {
		
		
		
		
		MessageObject mo = MessageObject.createMo(0, "新增数据失败，请联系管理员");
		
		int row = orderService.insert(order);
		if(row == 1) {
			mo = MessageObject.createMo(1, "新增数据成功");
		}
		return mo;
	}
	
	
	
	//删除功能
	@RequestMapping("/delete")
	@ResponseBody
	public MessageObject delete(Long orderId) {
		
		
		MessageObject mo = MessageObject.createMo(0,"亲，删除数据失败");
		
		int row = orderService.deleteByPrimaryKey(orderId);
		if(row == 1) {
			mo = MessageObject.createMo(1, "亲，删除数据成功");
		}
		
		
		
		return mo;
	}
	
	
	
	
	public static void main(String[] args) {
		
		/*
		 *  Apache 专门写一个Java语言增强包
		 *  commons-langx-x.x.jar  x :版本意思
		 *  
		 *  StringUtils 工具类专门对象String 进行增强
		 *  
		 *  判断字符串是否为空
		 *  1，不能为null
		 *  2，不能等于 ""
		 *  3, 不能等于有 空格 "   "
		 */
		
		System.out.println(StringUtils.isNotBlank(null));
		System.out.println(StringUtils.isNotBlank(""));
		System.out.println(StringUtils.isNotBlank("  "));
		System.out.println(StringUtils.isNotBlank("AA"));
		System.out.println("------------------------------------");
		System.out.println(StringUtils.isEmpty(null));
		System.out.println(StringUtils.isEmpty(""));
		System.out.println(StringUtils.isEmpty("  "));
		
		
		
	}
}
