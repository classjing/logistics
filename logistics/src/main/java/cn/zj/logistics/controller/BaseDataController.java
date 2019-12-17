package cn.zj.logistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import cn.zj.logistics.pojo.BaseDataExample;
import cn.zj.logistics.pojo.BaseDataExample.Criteria;
import cn.zj.logistics.service.BaseDataService;

@Controller
@RequestMapping("/baseData")
public class BaseDataController {
	
	@Autowired
	private BaseDataService baseDataService;
	
	
	
	
	
	@RequestMapping("/baseDataPage")
	public String baseDataPage() {
		
		
		return "baseDataPage";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<BaseData> list(@RequestParam(defaultValue = "1")Integer pageNum,
							   @RequestParam(defaultValue = "10")Integer pageSize,
							   String keyword){
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		BaseDataExample example = new BaseDataExample();
		
		//条件搜索
		if(StringUtils.isNotBlank(keyword)) {
			
			Criteria criteria1 = example.createCriteria();
			criteria1.andBaseNameLike("%"+keyword+"%");
		}
		
		List<BaseData> baseDatas = baseDataService.selectByExample(example );
		
		PageInfo<BaseData> pageInfo = new PageInfo<BaseData>(baseDatas);
		
		return pageInfo;
	}
	
	
	//获取所有的分类数据数据，以供角色分配分类数据使用
	@RequestMapping("/selectAllBaseData")
	@ResponseBody
	public List<BaseData> selectAllBaseData(){
		
		
		BaseDataExample example = new BaseDataExample();
		List<BaseData> baseDatas = baseDataService.selectByExample(example );
		
		return baseDatas;
	}

	//编辑功能，新增和修改通用
	@RequestMapping("/edit")
	public String edit(Model m,Long baseId) {
		
		//修改操作，根据baseId查询出BaseData对象并共享给baseDataEdit.jsp 进行数据回显
		if(baseId !=null) {
			BaseData baseData = baseDataService.selectByPrimaryKey(baseId);
			m.addAttribute("baseData", baseData);
		}
		
		//查询所有的父分类（parentId == null）
		BaseDataExample example = new BaseDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdIsNull();
		
		List<BaseData> baseDatas = baseDataService.selectByExample(example );
		m.addAttribute("baseDatas", baseDatas);
		
		return "baseDataEdit";
	}
	
	
	//修改功能
	@RequestMapping("/update")
	@ResponseBody
	public MessageObject update(BaseData baseData) {
		
		
		MessageObject mo = MessageObject.createMo(0, "修改数据失败，请联系管理员");
		
		int row = baseDataService.updateByPrimaryKeySelective(baseData);
		if(row == 1) {
			mo = MessageObject.createMo(1, "修改数据成功");
		}
		return mo;
	}
	
	
	
	//新增功能
	
	@RequestMapping("/insert")
	@ResponseBody
	public MessageObject insert(BaseData baseData) {
		
		
		MessageObject mo = MessageObject.createMo(0, "新增数据失败，请联系管理员");
		
		int row = baseDataService.insert(baseData);
		if(row == 1) {
			mo = MessageObject.createMo(1, "新增数据成功");
		}
		return mo;
	}
	
	
	
	//删除功能
	@RequiresPermissions("basicData:delete")
	@RequestMapping("/delete")
	@ResponseBody
	public MessageObject delete(Long baseId) {
		
		
		
		
		//根据当前基础数据查询是否还有子数据，有不能删除
		BaseDataExample example = new BaseDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(baseId);
		
		List<BaseData> baseDatas = baseDataService.selectByExample(example);
		
		MessageObject mo = MessageObject.createMo(0,"亲，删除数据失败");
		
		if(baseDatas.size() == 0) {
			int row = baseDataService.deleteByPrimaryKey(baseId);
			if(row == 1) {
				mo = MessageObject.createMo(1, "亲，删除数据成功");
			}
		}else {
			mo = MessageObject.createMo(0,"该分类数据还有子分类数据，不能删除");
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
