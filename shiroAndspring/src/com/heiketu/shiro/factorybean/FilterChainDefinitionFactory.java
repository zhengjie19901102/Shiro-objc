package com.heiketu.shiro.factorybean;

import java.util.LinkedHashMap;

/**
 * Shiro过滤链工厂类
 * @author zhengjie
 *
 */
public class FilterChainDefinitionFactory {
	/**
	 * FilterChainFactoryBeanBuilder
	 */
	public LinkedHashMap<String, String> FilterChainFactoryBeanBuilder(){
		LinkedHashMap<String,String> perm = new LinkedHashMap<String,String>();
		perm.put("/login.jsp","anon");
		perm.put("/shiroRequest/sessionTest","anon");
		perm.put("/user.jsp","roles[user]");
		perm.put("/shiroRequest/login","anon");
		perm.put("/**", "authc");
		return perm;
	}
}
