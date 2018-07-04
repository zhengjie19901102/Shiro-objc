package com.heiketu.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthenticatingRealm {

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//强转UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken)arg0;
		
		//用户名
		String username = upToken.getUsername();
		if("unkown".equals(username)) {
			throw new UnknownAccountException("未知用户");
		}

		//principal
		Object principal = username;
		//密码:加盐
		ByteSource bytes = ByteSource.Util.bytes("admin");
		String pass = new SimpleHash("MD5", "123456", bytes, 0).toString();
		String name2 = getName();
		//不加盐的设置
		//AuthenticationInfo info = new SimpleAuthenticationInfo(principal, pass, name2);
		AuthenticationInfo info = new SimpleAuthenticationInfo(username, pass, bytes, getName());
		return info;
	}

}
