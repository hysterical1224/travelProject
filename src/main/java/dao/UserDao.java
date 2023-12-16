package dao;

import domain.User;

public interface UserDao {

    public User findByUsername(String username);

    public void save(User user);
}
