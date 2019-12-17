package cn.zj.logistics.controller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.pojo.UserExample.Criteria;
import cn.zj.logistics.service.RoleService;
import cn.zj.logistics.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private RoleService roleService;
	
	
	@RequiresPermissions("admin:adminPage")
	@RequestMapping("/adminPage")
	public String adminPage() {
		System.out.println("AdminController.adminPage()");
		return "adminPage";
	}
	
	@RequiresPermissions("admin:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<User> list(@RequestParam(defaultValue = "1")Integer pageNum,
							   @RequestParam(defaultValue = "10")Integer pageSize,
							   String keyword){
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		UserExample example = new UserExample();
		
		//条件搜索
		if(StringUtils.isNotBlank(keyword)) {
			
			//根据账号模糊查询
			Criteria criteria1 = example.createCriteria();
			criteria1.andUsernameLike("%"+keyword+"%");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andRealnameLike("%"+keyword+"%");
			
			//获取关系 OR
			example.or(criteria2);
			
		}
		
		
		List<User> users = userService.selectByExample(example );
		
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		
		return pageInfo;
	}
	
	
	//编辑功能，新增和修改通用
	@RequestMapping("/edit")
	public String edit(Model m,Long userId) {
		
		
		//修改操作，根据userId查询出User对象并共享给adminEdit.jsp 进行数据回显
		if(userId !=null) {
			User user = userService.selectByPrimaryKey(userId);
			m.addAttribute("user", user);
			
		}
		
		//查询所有的角色共享给编辑页面，让其进行角色选择
		RoleExample example = new RoleExample();
		List<Role> roles = roleService.selectByExample(example);
		m.addAttribute("roles", roles);
		
		return "adminEdit";
	}
	
	
	//修改功能
	@RequestMapping("/update")
	@ResponseBody
	public MessageObject update(User user) {
		
		
		/*
		 * 密码加密
		 * 1，生成随机盐
		 * 2，密码+盐 MD5 加密三次
		 * 
		 */
		String salt = UUID.randomUUID().toString().substring(0, 4);
		user.setSalt(salt);
		Md5Hash md5Password = new Md5Hash(user.getPassword(), salt, 3);
		user.setPassword(md5Password.toString());
		
		
		MessageObject mo = MessageObject.createMo(0, "修改数据失败，请联系管理员");
		
		int row = userService.updateByPrimaryKeySelective(user);
		if(row == 1) {
			mo = MessageObject.createMo(1, "修改数据成功");
		}
		return mo;
	}
	
	
	
	
	
	
	//检查账号是否存在
	@RequestMapping("/checkUsername")
	@ResponseBody
	public boolean checkUsername(String username) {
		
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> users = userService.selectByExample(example );
		
		return users.size() == 0 ? true : false;
	}
	
	//新增功能
	
	@RequestMapping("/insert")
	@ResponseBody
	public MessageObject insert(User user) {
		
		//设置日期
		user.setCreateDate(new Date());
		
		/*
		 * 密码加密
		 * 1，生成随机盐
		 * 2，密码+盐 MD5 加密三次
		 * 
		 */
		String salt = UUID.randomUUID().toString().substring(0, 4);
		user.setSalt(salt);
		
		Md5Hash md5Password = new Md5Hash(user.getPassword(), salt, 3);
		user.setPassword(md5Password.toString());
		
		MessageObject mo = MessageObject.createMo(0, "新增数据失败，请联系管理员");
		
		int row = userService.insert(user);
		if(row == 1) {
			mo = MessageObject.createMo(1, "新增数据成功");
		}
		return mo;
	}
	
	
	
	//删除功能
	@RequestMapping("/delete")
	@ResponseBody
	public MessageObject delete(Long userId) {
		
		int row = userService.deleteByPrimaryKey(userId);
		
		MessageObject mo = MessageObject.createMo(0,"亲，删除数据失败");
		
		if(row == 1) {
			mo = MessageObject.createMo(1, "亲，删除数据成功");
		}
		
		return mo;
	}
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model m) {
		
		//获取Shiro表单认证过滤器共享的认证失败的错误信息
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
		
		System.out.println(shiroLoginFailure);
		//org.apache.shiro.authc.UnknownAccountException
		//org.apache.shiro.authc.IncorrectCredentialsException
		if(UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
			m.addAttribute("errorMsg", "账号不存在");
		}else if(IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
			m.addAttribute("errorMsg", "密码错误");
		}
		return "forward:/login.jsp";
	}
	
	
	
	
	@SuppressWarnings("resource")
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
		
		Scanner scanner = new Scanner(System.in);
		System.out.println(scanner.nextLine());
		
	}
}
