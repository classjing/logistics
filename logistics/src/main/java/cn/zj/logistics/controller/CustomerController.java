package cn.zj.logistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.BaseData;
import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import cn.zj.logistics.pojo.CustomerExample.Criteria;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.BaseDataService;
import cn.zj.logistics.service.CustomerService;
import cn.zj.logistics.service.UserService;
import cn.zj.logistics.utils.Constant;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private BaseDataService baseDataService;
	
	
	
	@RequestMapping("/customerPage")
	public String customerPage() {
		
		
		return "customerPage";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Customer> list(@RequestParam(defaultValue = "1")Integer pageNum,
							   @RequestParam(defaultValue = "10")Integer pageSize,
							   String keyword){
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		CustomerExample example = new CustomerExample();
		
		//获取Shiro的主体对象
		Subject subject = SecurityUtils.getSubject();
		//从主体获取身份
		User user = (User) subject.getPrincipal();
		
		
		Criteria criteria = example.createCriteria();
		//如果当前是业务员，只能查看自己的
		if(Constant.ROLE_SALESMAN.equals(user.getRolename())) {
			criteria.andUserIdEqualTo(user.getUserId());
		}
		
		//条件搜索
		if(StringUtils.isNotBlank(keyword)) {
			criteria.andCustomerNameLike("%"+keyword+"%");
		}
		
		List<Customer> customers = customerService.selectByExample(example );
		
		PageInfo<Customer> pageInfo = new PageInfo<Customer>(customers);
		
		return pageInfo;
	}
	
	
	//获取所有的权限数据，以供角色分配权限使用
	@RequestMapping("/selectAllCustomer")
	@ResponseBody
	public List<Customer> selectAllCustomer(){
		
		
		CustomerExample example = new CustomerExample();
		List<Customer> customers = customerService.selectByExample(example );
		
		return customers;
	}
	
	
	
	//编辑功能，新增和修改通用
	@RequestMapping("/edit")
	public String edit(Model m,Long customerId) {
		
		
		//修改操作，根据customerId查询出Customer对象并共享给customerEdit.jsp 进行数据回显
		if(customerId !=null) {
			Customer customer = customerService.selectByPrimaryKey(customerId);
			m.addAttribute("customer", customer);
			
		}
		
		//查询出所有的业务员
		UserExample example = new UserExample();
		cn.zj.logistics.pojo.UserExample.Criteria criteria = example.createCriteria();
		
		criteria.andRolenameEqualTo(Constant.ROLE_SALESMAN);
		
		List<User> users = userService.selectByExample(example);
		
		m.addAttribute("users", users);
		
		
		
		List<BaseData> baseDatas = baseDataService.selectBaseDataByParentName(Constant.BASIC_COMMON_INTERVAL);
		m.addAttribute("baseDatas", baseDatas);
		System.out.println(baseDatas);
		
		return "customerEdit";
	}
	
	
	//修改功能
	@RequestMapping("/update")
	@ResponseBody
	public MessageObject update(Customer customer) {
		
		
		MessageObject mo = MessageObject.createMo(0, "修改数据失败，请联系管理员");
		
		int row = customerService.updateByPrimaryKeySelective(customer);
		if(row == 1) {
			mo = MessageObject.createMo(1, "修改数据成功");
		}
		return mo;
	}
	
	
	
	//新增功能
	
	@RequestMapping("/insert")
	@ResponseBody
	public MessageObject insert(Customer customer) {
		
		
		MessageObject mo = MessageObject.createMo(0, "新增数据失败，请联系管理员");
		
		int row = customerService.insert(customer);
		if(row == 1) {
			mo = MessageObject.createMo(1, "新增数据成功");
		}
		return mo;
	}
	
	
	
	//删除功能
	@RequestMapping("/delete")
	@ResponseBody
	public MessageObject delete(Long customerId) {
		MessageObject mo = MessageObject.createMo(0,"亲，删除数据失败");
		int row = customerService.deleteByPrimaryKey(customerId);
		if(row == 1) {
			mo = MessageObject.createMo(1, "亲，删除数据成功");
		}
		return mo;
	}
	
}
