package com.heiketu.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
/**
 * 认证继承 ：AuthenticatingRealm实现类
 * 		或实现:Realm接口
 * 授权继承: AuthorizingRealm实现类
 * |
 * | AuthenticatingRealm与AuthorizingRealm均实现了Realm接口
 * | AuthenticatingRealm又是AuthorizingRealm父类
 * |
 * @author zhengjie
 *
 */
public class SecondRealm extends AuthorizingRealm {

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//强转UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken)arg0;
		//用户名
		String username = upToken.getUsername();
		if("windows".equals(username)) {
			throw new UnknownAccountException("Windows用户");
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

	/**
	 * 授权在这里实现
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**
		 * the primary principal used to uniquely identify the owning account/Subject
		 */
		Object primaryPrincipal = principals.getPrimaryPrincipal();
		//权限集合
		Set<String> roles = new HashSet<>();
		//判断当前登录用户名
		if("admin".equals(primaryPrincipal)) {
			//如果当前用户登录的是"admin",则拥有访问user的权限
			roles.add("user");
		}
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
		return simpleAuthorizationInfo;
	}

}
