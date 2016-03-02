package com.wzhang.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wzhang.common.RoleConstants;
import com.wzhang.dao.UserDao;
import com.wzhang.dao.impl.UserDaoImpl;
import com.wzhang.domain.UserBean;

/**
 * 自定义用户与权限的关系
 * @author wzhang
 *
 */
public class CustomUserDetailsService implements UserDetailsService {
	protected static Logger logger = Logger.getLogger("service");
	private UserDao userDAO = new UserDaoImpl();
	
	/**
	 * 根据用户名获取用户-权限等用户信息
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDetails user = null;
		try {
			UserBean dbUser = userDAO.getUser(username);
			user = new User(dbUser.getUserName(), dbUser.getPassword().toLowerCase(), true, true, true, true,getAuthorities(dbUser));
		} catch (Exception e) {
			logger.error("Error in retrieving user");  
            throw new UsernameNotFoundException("Error in retrieving user"); 
		}
		return user;
	}
	
	 /** 
     * 获得访问角色权限 
     *  
     * @param access 
     * @return 
     */  
	private Collection<GrantedAuthority> getAuthorities(UserBean dbUser) {  
  
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);  
  
        // 所有的用户默认拥有ROLE_USER权限  
        logger.debug("Grant ROLE_USER to this user");  
        authList.add(new  SimpleGrantedAuthority(RoleConstants.ROLE_USER));  
  
        // 如果参数access为1.则拥有ROLE_ADMIN权限  
        if (dbUser.getRole().getRoleName().equals(RoleConstants.ROLE_ADMIN)) {  
            logger.debug("Grant ROLE_ADMIN to this user");  
            authList.add(new  SimpleGrantedAuthority(RoleConstants.ROLE_ADMIN));  
        }  
  
        return authList;  
    }  
	
	

}
