package servlet;

import service.impl.UserRegisterImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ActiveUserServlet", value = "/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String msg = null;
        if (code !=null){
            //2.调用service完成激活
            UserRegisterImpl service = new UserRegisterImpl();
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
}
