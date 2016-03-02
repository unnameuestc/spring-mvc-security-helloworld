package com.wzhang.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.wzhang.dao.ResourceDao;
import com.wzhang.dao.impl.ResourceDaoImpl;

/**
 * 资源数据权限定义，即定义某一资源可以被哪些角色访问
 * @author wzhang
 *
 */
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource  {

	private static final Logger logger = Logger
			.getLogger(CustomFilterInvocationSecurityMetadataSource .class);

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	private ResourceDao resourceDao;	
	
	public CustomFilterInvocationSecurityMetadataSource(ResourceDao resourceDao ){
		this.resourceDao =resourceDao;
		resourceMap = loadResourceMatchAuthority();
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {

		return null;
	}

	public CustomFilterInvocationSecurityMetadataSource  () {
		super();
		this.resourceDao  = new ResourceDaoImpl();
		resourceMap = loadResourceMatchAuthority();
	}

	/**
	 * 加载资源与权限的映射关系
	 * 
	 * @return
	 */
	private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {

		Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();

		// 获取资源权限映射key：url，value：role
		Map<String, String> configs = resourceDao.getResources();
		for (Entry<String, String> entry : configs.entrySet()) {
			Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();

			System.out.println("entry的值：" + entry.toString());
			String[] vals = entry.getValue().split(",");
			for (String val : vals) {
				ConfigAttribute config = new SecurityConfig(val);
				list.add(config);
			}
			map.put(entry.getKey(), list);
		}

		return map;

	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();

		System.out.println("requestUrl is " + url);
		logger.info("requestUrl is " + url);
		
		if (resourceMap == null) {
			loadResourceMatchAuthority();
		}
		//比较url是否存在
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (pathMatcher.match(resURL,url)) {
				return resourceMap.get(resURL);
			}
		}
		return resourceMap.get(url);
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
