package cn.zj.logistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;
import cn.zj.logistics.pojo.RoleExample.Criteria;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.RoleService;
import cn.zj.logistics.service.UserService;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping("/rolePage")
	public String rolePage() {
		
		
		return "rolePage";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Role> list(@RequestParam(defaultValue = "1")Integer pageNum,
							   @RequestParam(defaultValue = "10")Integer pageSize,
							   String keyword){
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		RoleExample example = new RoleExample();
		
		//条件搜索
		if(StringUtils.isNotBlank(keyword)) {
			
			Criteria criteria1 = example.createCriteria();
			criteria1.andRolenameLike("%"+keyword+"%");
		}
		
		List<Role> roles = roleService.selectByExample(example );
		
		PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
		
		return pageInfo;
	}
	
	
	//编辑功能，新增和修改通用
	@RequestMapping("/edit")
	public String edit(Model m,Long roleId) {
		
		//修改操作，根据roleId查询出Role对象并共享给roleEdit.jsp 进行数据回显
		if(roleId !=null) {
			Role role = roleService.selectByPrimaryKey(roleId);
			m.addAttribute("role", role);
			
		}
		return "roleEdit";
	}
	
	
	//修改功能
	@RequestMapping("/update")
	@ResponseBody
	public MessageObject update(Role role) {
		
		MessageObject mo = MessageObject.createMo(0, "修改数据失败，请联系管理员");
		int row = roleService.updateByPrimaryKeySelective(role);
		if(row == 1) {
			mo = MessageObject.createMo(1, "修改数据成功");
		}
		return mo;
	}
	
	
	
	//新增功能
	@RequestMapping("/insert")
	@ResponseBody
	public MessageObject insert(Role role) {
		
		
		MessageObject mo = MessageObject.createMo(0, "新增数据失败，请联系管理员");
		
		int row = roleService.insert(role);
		if(row == 1) {
			mo = MessageObject.createMo(1, "新增数据成功");
		}
		return mo;
	}
	
	
	
	//删除功能
	@RequestMapping("/delete")
	@ResponseBody
	public MessageObject delete(Long roleId) {
		
		
		//删除前先判断此角色还是否拥有用户，有不能删除
		
		UserExample example = new UserExample();
		
		cn.zj.logistics.pojo.UserExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		
		List<User> users = userService.selectByExample(example );
		
		
		MessageObject mo = MessageObject.createMo(0,"亲，删除数据失败");
		if(users.size() == 0) {
			
			int row = roleService.deleteByPrimaryKey(roleId);
			
			if(row == 1) {
				mo = MessageObject.createMo(1, "亲，删除数据成功");
			}
			
		}else {
			mo = MessageObject.createMo(0, "亲，此角色还有用户，不能删除");
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
