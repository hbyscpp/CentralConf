package com.seaky.centralconf.manager.service;

import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractFilterChainDefinitionsService implements FilterChainDefinitionsService {
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Autowired
	CacheManager cacheManager;
	public void updatePermission() throws Exception {
		synchronized (shiroFilterFactoryBean) {
			AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
			// 获取过滤管理器
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
			// 清空初始权限配置
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			// 重新构建生成
			shiroFilterFactoryBean.setFilterChainDefinitionMap(initOtherPermission());
//			shiroFilterFactoryBean.setFilterChainDefinitions("");
			Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim().replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
		}
	}

	public abstract Map<String, String> initOtherPermission() throws Exception;
}
