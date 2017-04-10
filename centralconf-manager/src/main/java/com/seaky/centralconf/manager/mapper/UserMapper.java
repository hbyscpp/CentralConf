package com.seaky.centralconf.manager.mapper;

import com.seaky.centralconf.manager.entry.po.User;

import tk.mybatis.mapper.common.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
	/**
	 * 用户
	 * 
	 * @param userName
	 */

	public User getUserByName(String userName);

	public void delAllUser();

	public Boolean isUserExist(String userName);

	public User findUser(Long id);
}
