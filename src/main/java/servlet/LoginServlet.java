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

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo result = new ResultInfo();
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次

        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            result.setFlag(false);
            result.setErrorMsg("验证码错误");

            //将info数据序列化为Json
            ObjectMapper om = new ObjectMapper();
            String s = om.writeValueAsString(result);

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

        UserRegisterImpl ur = new UserRegisterImpl();
        User u = ur.login(user);



        if (u==null){
            result.setFlag(false);
            result.setData("用户名或密码错误！");
        }

        if (u!=null && "N".equals(u.getStatus())){
            //激活了
            result.setFlag(false);
            result.setErrorMsg("您尚未激活，请激活！");
        }

        if (u!=null && "Y".equals(u.getStatus())){
            //激活了
            result.setFlag(true);
            result.setErrorMsg("登录成功！");
            session.setAttribute("user", u);

        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        om.writeValue(response.getOutputStream(),result);




    }
}
