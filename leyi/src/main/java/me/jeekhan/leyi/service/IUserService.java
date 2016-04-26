package me.jeekhan.leyi.service;

import me.jeekhan.leyi.model.User;

public interface IUserService {
	public User getUser(int id);
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(int user);

}
