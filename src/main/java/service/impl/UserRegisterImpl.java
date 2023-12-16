package service.impl;

import dao.impl.UserDaoImpl;
import domain.User;
import service.UserService;
import utils.MailUtils;

import java.util.UUID;

public class UserRegisterImpl implements UserService {
    private UserDaoImpl userDao = new UserDaoImpl();

    public boolean register(User user){
        /**
         * 1.查找数据库是否有注册的用户名。有，注册失败；没有，表明可以注册。
         * 2.没有的情况下，进行注册，首先保存用户信息，生成一个UUID，设置状态码为N
         *                      然后将UUID和改变状态码的链接通过mailUtils发送到邮箱
         *                      服务器根据mail的访问进行状态码的修改
         *
         * */


        //1.根据用户名查询用户对象
        User username = userDao.findByUsername(user.getUsername());
        if (username != null){
            //用户存在，注册失败
            return false;
        }

        //2.保存用户信息
        user.setStatus("N");
        //初始化为没激活
        //2.1设置激活码
        user.setCode(UUID.randomUUID().toString());

        String content = "<a href='http://localhost:8080/user/active?code="+user.getCode()+"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),content, "激活邮件");
        userDao.save(user);
        return true;
    }

    public boolean active(String code){
        User user = userDao.findByCode(code);

       if (user!=null){
           //如果查询到的用户不为空，则进行激活处理
           userDao.updateStatus(user);

           return true;
       }

       return false;
    }

    public User login(User user){

        User u = userDao.findByUsernameAndPassword(user);


        return u;
    }


}
