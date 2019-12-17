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
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.pojo.PermissionExample.Criteria;
import cn.zj.logistics.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	
	
	
	
	@RequestMapping("/permissionPage")
	public String permissionPage() {
		
		
		return "permissionPage";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Permission> list(@RequestParam(defaultValue = "1")Integer pageNum,
							   @RequestParam(defaultValue = "10")Integer pageSize,
							   String keyword){
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		PermissionExample example = new PermissionExample();
		
		//条件搜索
		if(StringUtils.isNotBlank(keyword)) {
			
			Criteria criteria1 = example.createCriteria();
			criteria1.andNameLike("%"+keyword+"%");
		}
		
		List<Permission> permissions = permissionService.selectByExample(example );
		
		PageInfo<Permission> pageInfo = new PageInfo<Permission>(permissions);
		
		return pageInfo;
	}
	
	
	//获取所有的权限数据，以供角色分配权限使用
	@RequestMapping("/selectAllPermission")
	@ResponseBody
	public List<Permission> selectAllPermission(){
		
		
		PermissionExample example = new PermissionExample();
		List<Permission> permissions = permissionService.selectByExample(example );
		
		return permissions;
	}
	
	
	
	//编辑功能，新增和修改通用
	@RequestMapping("/edit")
	public String edit(Model m,Long permissionId) {
		
		
		//修改操作，根据permissionId查询出Permission对象并共享给permissionEdit.jsp 进行数据回显
		if(permissionId !=null) {
			Permission permission = permissionService.selectByPrimaryKey(permissionId);
			m.addAttribute("permission", permission);
			
		}
		
		//查询所有的父权限（type=menu）的权限共享给编辑页面，让其进行父权限选择
		PermissionExample example = new PermissionExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo("menu");
		
		List<Permission> permissions = permissionService.selectByExample(example );
		m.addAttribute("permissions", permissions);
		
		return "permissionEdit";
	}
	
	
	//修改功能
	@RequestMapping("/update")
	@ResponseBody
	public MessageObject update(Permission permission) {
		
		
		MessageObject mo = MessageObject.createMo(0, "修改数据失败，请联系管理员");
		
		int row = permissionService.updateByPrimaryKeySelective(permission);
		if(row == 1) {
			mo = MessageObject.createMo(1, "修改数据成功");
		}
		return mo;
	}
	
	
	
	//新增功能
	
	@RequestMapping("/insert")
	@ResponseBody
	public MessageObject insert(Permission permission) {
		
		
		MessageObject mo = MessageObject.createMo(0, "新增数据失败，请联系管理员");
		
		int row = permissionService.insert(permission);
		if(row == 1) {
			mo = MessageObject.createMo(1, "新增数据成功");
		}
		return mo;
	}
	
	
	
	//删除功能
	@RequestMapping("/delete")
	@ResponseBody
	public MessageObject delete(Long permissionId) {
		
		
		
		
		//根据当前权限id查询是否有子权限
		PermissionExample example = new PermissionExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(permissionId);
		
		List<Permission> permissions = permissionService.selectByExample(example);
		
		MessageObject mo = MessageObject.createMo(0,"亲，删除数据失败");
		
		if(permissions.size() == 0) {
			int row = permissionService.deleteByPrimaryKey(permissionId);
			if(row == 1) {
				mo = MessageObject.createMo(1, "亲，删除数据成功");
			}
		}else {
			mo = MessageObject.createMo(0,"该权限还有子权限，不能删除");
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
