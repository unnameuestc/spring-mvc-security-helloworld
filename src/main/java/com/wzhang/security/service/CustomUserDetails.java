package com.wzhang.security.service;

import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	/**
	 * 用戶名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 角色
	 */
	private String role;

	private boolean enabled;

	private static final String ROLE_LOGIN = "ROLE_LOGIN";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2690825971444232853L;

	protected static Logger logger = Logger.getLogger("service");

	 /**
     * 获取当前用户的权限
     * 其实用户应该拥有多个角色,这里简单起见只用了一个String类型来表示
     * 其实用户 角色权限 资源三者可以各自创建对象并关联能实现一个非常复杂的权限控制
     */
	@SuppressWarnings("deprecation")
	public Collection<GrantedAuthority> getAuthorities() {
		
		GrantedAuthority[] gas = new GrantedAuthority[2];
		gas[0] = new GrantedAuthorityImpl(role);
		// 分配登录权限给所有通过登录验证的角色
		gas[1] = new GrantedAuthorityImpl(ROLE_LOGIN);
		return Arrays.asList(gas);
		
	}

	public CustomUserDetails() {
		super();
	}

	public CustomUserDetails(String username, String password, String role,
			boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
