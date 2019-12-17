package cn.zj.logistics.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.pojo.UserExample.Criteria;
import cn.zj.logistics.service.PermissionService;
import cn.zj.logistics.service.UserService;

public class CustomRealm extends AuthorizingRealm {
	
	/*
	 * 认证方法，此方法返回认证信息对象AuthenticationInfo 
	 * 开发者在此方法内部完成自定义的认证逻辑
	 * 
	 * 认证思路
	 * 
	 * 1，获取tolen的身份 （账号）
	 * 	String username = (String) token.getPrincipal();
	 * 
	 * 2，注入UserService userService;
	 * 
	 * 3, 调用UserService中的根据账号查询用户信息的方法
	 * User user = userService.selectByUsername(username);
	 * 
	 * 	3,1如果账号不存在，此方法返回null	
	 * 	认证的代码地方就会抛出一个 UnknownAccountException 账号不存在异常
	 * 
	 * 	3,2如果账号存在，获取 User对象中的密码
	 * 	String password = user.getPassword();
	 * 
	 * 
	 * 4,创建AuthenticationInfo 传入 身份（User对象）和凭证（密码），Shiro底层会自动
	 * 	把用户token中的密码，和 User对象中获取的密码进行匹配
	 * 
	 * 	4.1 匹配失败
	 * 	认证的代码地方就会抛出一个 IncorrectCredentialsException 无效的凭证异常（秘密错误）
	 * 
	 *  4.2 密码比对成功
	 *  	------认证完成
	 * 
	 */
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//1.获取Token中的身份
		String principal = (String) token.getPrincipal();
		
		//2, 调用UserService中的根据账号查询用户信息的方法
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(principal);
		List<User> users = userService.selectByExample(example);
		
		if(users.size() ==0) {
			return null;
		}
		User user = users.get(0);
		//获取user的密码
		String hashedCredentials = user.getPassword();
		//获取盐
		String salt = user.getSalt();
		
		//创建认证信息对象
		//SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, credentials, this.getName());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, hashedCredentials, ByteSource.Util.bytes(salt), this.getName());
		
		return authenticationInfo;
	}
	
	
	// 授权方法
		/*
		 * 此方法，在用户访问需要拥有权限的资源会执行
		 * 
		 * 开发者需要自己在方法内部完成授权的逻辑
		 * 
		 * 授权思路
		 * 
		 * 1，获取当前认证通过的身份信息对象 ，就是 user对象
		 * User user = principals.getPrimaryPrincipal();
		 *  user对象包含当前身份的所有信息 
		 *  	角色id，角色对应的权限的id集合 ： 1,20,18,15,30
		 * 2,将 当前用户对应的角色所有的权限 的id 使用逗号切割成数组，变成一个个权限的id值
		 * 	Long permissionIds = {1,20,18,15,30};
		 * 	
		 * 3，根据一个个权限的id去权限表中t_permission 查询出对应的权限id的权限表达式
		 *  List<String> permissions = permissionService.selectExpressionsByIds(permissionIds)
		 * 	查询出一个权限表达式集合
		 * 		user:insert
		 * 		user:update
		 * 		user:delete
		 * 		teacher:list
		 * 		student:studentPage
		 * 		.....
		 * 
		 * 4,创建 授权信息对象AuthorizationInfo
		 * 
		 * 5,将第三步查询的所有权限表达式添加的授权信息对象中AuthorizationInfo
		 * 
		 * 
		 * 6， Shiro 框架自己完成
		 * 	运行时候，Shiro框架底层，将用户判断的权限表达式和 第五步操作权限表达集合进行比对
		 * 	如果有：拥有权限，放行
		 *  没有：没有权限，不放行
		 */
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		//1.获取认证通过的身份
		User user = (User) principals.getPrimaryPrincipal();
		System.out.println("user :"+user);
		
		//2.获取当前身份的对应的角色的所有的权限id
		String permissionIds = user.getPermissionIds();
		
		String[] permissionIdsArr = permissionIds.split(",");
		
		
		//创建集合，将字符串的数组的值转换成Long类型
		List<Long> permissionIdsList = new ArrayList<>();
		//循环集合
		for (String permissionId : permissionIdsArr) {
			permissionIdsList.add(Long.valueOf(permissionId));
		}
		
		//根据权限的id集合查询出所有的权限表达式
		List<String> permissions = permissionService.selectExpressionsByIds(permissionIdsList);
		
		//创建 授权信息对象AuthorizationInfo
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		//查询的所有权限表达式添加的授权信息对象中AuthorizationInfo
		authorizationInfo.addStringPermissions(permissions);
		
		return authorizationInfo;
	}

	

}
