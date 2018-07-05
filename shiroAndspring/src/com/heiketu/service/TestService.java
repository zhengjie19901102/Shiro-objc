package com.heiketu.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	public void testService() {
		Session session = SecurityUtils.getSubject().getSession();
		System.out.println("Service:--->" + session.getAttribute("key"));
	}
	
}
