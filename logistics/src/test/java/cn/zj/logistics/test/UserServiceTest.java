package cn.zj.logistics.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserServiceTest {
	
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testSelectByExample() {
		
		
		
		/*
		 *  MyBatisPageHelper 分页工具类
		 *  int pageNum; 页码，默认第一页 1
			int pageSize; 每页条数  当前默认 10条
			PageHelper.startPage(pageNum, pageSize);
		 * 
		 */
		
		int pageNum = 2;
		int pageSize = 10;
		PageHelper.startPage(pageNum, pageSize);
		
		
		UserExample example = new UserExample();
		List<User> users = userService.selectByExample(example );
		
		/*
		 * PageInfo<User> pageInfo = new PageInfo<User>(users);
		 * 分页信息对象
		 * 	封装当前分页的所有信息
		 * 如
		 * 	总记录数
		 *  每页的结果集
		 *  当前页
		 *  上一页
		 *  下一页
		 *  .....
		 * 
		 * 
		 */
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		System.out.println(pageInfo);
		
	}
	

	@Test
	public void testDeleteByPrimaryKey() {
	}

	@Test
	public void testInsert() {
	}

	

	@Test
	public void testSelectByPrimaryKey() {
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

}
