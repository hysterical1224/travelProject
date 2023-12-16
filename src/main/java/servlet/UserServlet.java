package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.UserRegisterImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/user/*")
public class UserServlet extends BaseServlet {

    private final UserRegisterImpl service = new UserRegisterImpl();
    ResultInfo resultInfo = new ResultInfo();
    ObjectMapper om = new ObjectMapper();

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        System.out.println("userServlet的add方法...");
    }

    protected void find(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        System.out.println("userServlet的find方法...");
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("userServlet的login方法...");



        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次

        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");

            //将info数据序列化为Json
//            ObjectMapper om = new ObjectMapper();
            String s = om.writeValueAsString(resultInfo);

            response.setContentType("application/json;charset=utf-8");

            response.getWriter().write(s);
            return;
        }


        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        UserRegisterImpl ur = new UserRegisterImpl();
        User u = service.login(user);



        if (u==null){
            resultInfo.setFlag(false);
            resultInfo.setData("用户名或密码错误！");
        }

        if (u!=null && "N".equals(u.getStatus())){
            //激活了
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您尚未激活，请激活！");
        }

        if (u!=null && "Y".equals(u.getStatus())){
            //激活了
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("登录成功！");
            session.setAttribute("user", u);

        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        om.writeValue(response.getOutputStream(),resultInfo);



    }


    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("userServlet的register方法...");

//        ResultInfo resultInfo = new ResultInfo();
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次
        response.setContentType("application/json;charset=utf-8");
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");

            //将info数据序列化为Json

            String s = om.writeValueAsString(resultInfo);

//            response.setContentType("application/json;charset=utf-8");

            response.getWriter().write(s);
            return;
        }


        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取数据

        //封装对象
        User user = new User();
//        UserRegisterImpl userLogin = new UserRegisterImpl();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service完成注册
        boolean flag = service.register(user);
        resultInfo.setFlag(flag);
        if (flag){
            //注册成功

            resultInfo.setErrorMsg("注册成功");
//            response.sendRedirect("/register_ok.html");
            String s = om.writeValueAsString(resultInfo);
            response.getWriter().write(s);
            return;

        } else {
            //注册失败
            resultInfo.setErrorMsg("注册失败,用户存在");
        }

        //将info数据序列化为Json
//        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(resultInfo);



        response.getWriter().write(s);
    }


    protected void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("userServlet的active方法...");
        String code = request.getParameter("code");
        String msg = null;
        if (code !=null){
            //2.调用service完成激活
//            UserRegisterImpl service = new UserRegisterImpl();
            Boolean flag = service.active(code);

            if (flag){
                //激活成功
                msg = "激活成功，请使用<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);

        } else{
            //激活失败
            msg = "激活失败!";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }

    protected void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("userServlet的findUser方法...");
        //查询当前用户状态。用途：tips、查询当前用户是否登录
        Object user = request.getSession().getAttribute("user");


        response.setContentType("application/json;charset=utf-8");

        om.writeValue(response.getOutputStream(),user);

    }


    protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("userServlet的exit方法...");
        //销毁session
        request.getSession().invalidate();

        //跳转登录界面
        response.sendRedirect(request.getContextPath()+"/login.html");


    }
}
