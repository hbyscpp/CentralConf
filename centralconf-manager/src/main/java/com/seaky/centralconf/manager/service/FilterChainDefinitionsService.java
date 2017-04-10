package com.seaky.centralconf.manager.service;

import java.util.Map;

public interface FilterChainDefinitionsService {

  
    /** 重新加载框架权限资源配置 (强制线程同步) 
     * @throws Exception */  
    public abstract void updatePermission() throws Exception;  
  
    /** 初始化第三方权限资源配置 */  
    public abstract Map<String, String> initOtherPermission() throws Exception;  
}
