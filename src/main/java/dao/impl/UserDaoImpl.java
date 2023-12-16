package dao.impl;


import dao.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDs());


    @Override
    public User findByUsername(String username) {
        String sql = "select * from tab_user where username=?";
        List<User> users = jt.query(sql,
                new BeanPropertyRowMapper<>(User.class),
                username);

        if (users.isEmpty()) {
            return null;
        }

        // 假定只有一个匹配的用户，如果有多个匹配结果，这里只取第一个
        return users.get(0);
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        int col = jt.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
        System.out.println(user.toString());

    }

    /**
     * 根据激活码查询用户对象
     *
     * */
    public User findByCode(String code) {
        String sql= "select * from tab_user where code=?";
        List<User> users = jt.query(sql, new BeanPropertyRowMapper<>(User.class), code);
        if (users.isEmpty()) {
            return null;
        }

        // 假定只有一个匹配的用户，如果有多个匹配结果，这里只取第一个
        return users.get(0);
    }

    public void updateStatus(User user) {
        String sql = "update tab_user set status='Y' where uid=?";
        jt.update(sql,user.getUid());
    }

    public User findByUsernameAndPassword(User user){
        String sql = "select * from tab_user where username=? and password=?";
        List<User> users = jt.query(sql,
                new BeanPropertyRowMapper<>(User.class),
                user.getUsername(),user.getPassword());

        if (users.isEmpty()) {
            return null;
        }

        // 假定只有一个匹配的用户，如果有多个匹配结果，这里只取第一个
        return users.get(0);
    }
}
