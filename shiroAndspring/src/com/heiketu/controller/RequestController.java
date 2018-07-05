package com.heiketu.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heiketu.service.TestService;

@Controller
@RequestMapping("/shiroRequest")
public class RequestController {

	@Autowired
	private TestService serviceTest;
	
	
	@RequestMapping("/login")
	public String login(String username, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		/**
		 * 是否已经认证:没有认证则调用获取用户密码，
		 */
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			try {
				System.out.println("OUT HASHCODE:" + token);
				currentUser.login(token);
			} catch (Exception uae) {
				System.out.println("验证失败:" + uae);
			}
		}
		return "redirect:/success.jsp";
	}

	
	//测试加密算法
	public static void main(String[] args) {
		/**
		 * 加盐前:e10adc3949ba59abbe56e057f20f883e
		 * 加盐后:a66abb5684c45962d887564f08346e8d
		 */
		SimpleHash simpleHash = new SimpleHash("MD5", "123456", null, 0);
		System.out.println(simpleHash);
		//加盐设置
		ByteSource salt =  ByteSource.Util.bytes("admin");
		SimpleHash simpleHash2 = new SimpleHash("MD5", "123456", salt, 0);
		System.out.println(simpleHash2);
	}
	
	
	
	/**
	 * 测试Shiro的Session
	 */
	@RequestMapping(value="/sessionTest")
	public String sessionTest(HttpSession session) {
		
		session.setAttribute("key", "value");
		
		serviceTest.testService();
		
		
		return null;
	}
	
	
}
