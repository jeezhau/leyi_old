package me.jeekhan.leyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.dao.UserMapper;
import me.jeekhan.leyi.model.User;
import me.jeekhan.leyi.service.IUserService;

@Service("userService")
@Scope("singleton")
public class UserService implements IUserService{
	@Autowired
	private UserMapper userMapper;
	@Override
	public User getUser(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public void deleteUser(int id) {
		userMapper.deleteByPrimaryKey(id);
		
	}
}
