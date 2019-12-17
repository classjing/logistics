package cn.zj.logistics.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import cn.zj.logistics.pojo.User;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	/*
	 * 
	 * Shiro框架的拒绝访问
	 * 
	 * 	如果方法返回 true，进行下一步操作
	 * 	如果方法返回 false ，直接终止shiro后面代码运行
	 * 
	 *     可以在方法进行验证码的处理
	 *  1，先从request请求对象中获取 用户提交表单参数 验证码
	 *  2，从Session获取共享的 随机码
	 *  3，比对用户提交的验证码和Session中共享的验证码是否相同（验证码不区分大小写）
	 *   3.1，不相同 ：return false 
	 *   3.2，相同：直接调用父类方法，继续父类代码操作
	 * 
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest)request;
		
		//1.获取用户表单提交的验证码
		String verifyCode = req.getParameter("verifyCode");
		
		//2获取Session中的随机码
		String rand = (String) req.getSession().getAttribute("rand");
		
		if(StringUtils.isNotBlank(verifyCode) && StringUtils.isNotBlank(rand)) {
			if(!verifyCode.equalsIgnoreCase(rand)) {
				//共享数据
				req.setAttribute("errorMsg", "亲，验证码不正确");
				req.getRequestDispatcher("/login.jsp").forward(req, response);
				return false;
			}
		}
		
		
		
		return super.onAccessDenied(request, response);
	}
	
	
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//从请求中获取Shiro的 主体
		Subject subject = getSubject(request, response);
		//从主体中获取Shiro框架的Session
		Session session = subject.getSession();
		//如果主体没有认证（Session中认证）并且 主体已经设置记住我了
		if (!subject.isAuthenticated() && subject.isRemembered()) {
			//获取主体的身份（从记住我的Cookie中获取的）
			User principal = (User) subject.getPrincipal();
			//将身份认证信息共享到 Session中
			session.setAttribute("principal", principal);
			
		}
		return subject.isAuthenticated() || subject.isRemembered();
	}
}
