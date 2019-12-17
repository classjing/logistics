package cn.zj.logistics.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	
	@RequestMapping("/index")
	public String index(HttpSession session) {
		
		Subject subject = SecurityUtils.getSubject();
		Object user = subject.getPrincipal();
		session.setAttribute("user", user);
		
		return "index";
	}
	
	
	@RequestMapping("/welcome")
	public String welcome() {
		
		return "welcome";
	}
}
