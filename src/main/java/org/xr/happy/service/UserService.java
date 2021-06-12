package org.xr.happy.service;

import org.xr.happy.model.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    public User createUser(final int c);

    List<User> queryUser(Long userId,String keywords);
}
