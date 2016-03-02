package com.wzhang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.wzhang.common.RoleConstants;
import com.wzhang.dao.ResourceDao;

public class ResourceDaoImpl implements ResourceDao {

	/**
	 * 获取所有资源权限映射
	 * key-URL
	 * value-Role
	 */
	public Map<String, String> getResources() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("/admin**", RoleConstants.ROLE_ADMIN);
		map.put("/index**", RoleConstants.ROLE_USER);
		return map;
	}

}
