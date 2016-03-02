package com.wzhang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wzhang.common.RoleConstants;
import com.wzhang.dao.UserDao;
import com.wzhang.domain.RoleBean;
import com.wzhang.domain.UserBean;

public class UserDaoImpl implements UserDao {
	protected static Logger logger = Logger.getLogger("dao");  
	public UserBean getUser(String userName) {
		List<UserBean> users = internalDatabase();  
		  
        for (UserBean ub : users) {  
            if (ub.getUserName().equals(userName)) {  
                logger.debug("User found");  
                return ub;  
            }  
        }  
        logger.error("User does not exist!");  
        throw new RuntimeException("User does not exist!");
	}
	
	
	 private List<UserBean> internalDatabase() {  
		  
	        List<UserBean> users = new ArrayList<UserBean>();  
	        UserBean user = null;  
	  
	        //创建用户admin/admin，角色ROLE_ADMIN
	        user = new UserBean();  
	        user.setUserName("admin");  	  
	        // "admin"经过MD5加密后  
	        user.setPassword("21232f297a57a5a743894a0e4a801fc3");
	        user.setAccess(1);
	        user.setRole(new RoleBean(1,RoleConstants.ROLE_ADMIN,""));	  
	        users.add(user);  
	  
	        //创建用户user/user，角色ROLE_USER
	        user = new UserBean();  
	        user.setUserName("user");  	  
	        // "user"经过MD5加密后  
	        user.setPassword("ee11cbb19052e40b07aac0ca060c23ee");
	        user.setAccess(2);  
	        user.setRole(new RoleBean(2,RoleConstants.ROLE_USER,""));	  
	        users.add(user);  
	  
	        return users;  
	  
	    }  

}
