package servlet;

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

@WebServlet(name = "registerUserServlet", value = "/users")
public class registerUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次

        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");

            //将info数据序列化为Json
            ObjectMapper om = new ObjectMapper();
            String s = om.writeValueAsString(resultInfo);

            response.setContentType("application/json;charset=utf-8");

            response.getWriter().write(s);
            return;
        }


        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取数据

        //封装对象
        User user = new User();
        UserRegisterImpl userLogin = new UserRegisterImpl();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service完成注册
        boolean flag = userLogin.register(user);
        resultInfo.setFlag(flag);
        if (flag){
            //注册成功

            resultInfo.setErrorMsg("注册成功");

        } else {
            //注册失败
            resultInfo.setErrorMsg("注册失败,用户存在");
        }

        //将info数据序列化为Json
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(resultInfo);

        response.setContentType("application/json;charset=utf-8");

        response.getWriter().write(s);



    }
}
