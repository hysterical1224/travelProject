package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@WebServlet(name = "BaseServlet", value = "/baseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        System.out.println("请求的uri："+uri);

        //2.获得方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("方法名称："+methodName);
        //uri.lastIndexOf('/') 是在URI字符串中找到最后一个斜杠 ("/") 的位置，
        // 这个位置将用作截取字符串的起始点。通过 uri.lastIndexOf('/') + 1，
        // 你会得到最后一个斜杠之后的字符的索引

        //3.获取方法对象method
        //谁调用我？我代表谁
        System.out.println(this);


        try {
            //忽略访问权限修饰符，获取方法
            Method method = this.getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);

            //暴力反射
            method.setAccessible(true);

            //执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    public void writeValue(Object obj, HttpServletResponse resp) throws IOException {
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        om.writeValue(resp.getOutputStream(),obj);
    }

    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }


    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        //完成方法分发
        //1.获取请求路径

//        String uri = req.getRequestURI();
//        System.out.println("请求的uri："+uri);
//
//        //2.获得方法名称
//        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println("方法名称："+methodName);
//        //uri.lastIndexOf('/') 是在URI字符串中找到最后一个斜杠 ("/") 的位置，
//        // 这个位置将用作截取字符串的起始点。通过 uri.lastIndexOf('/') + 1，
//        // 你会得到最后一个斜杠之后的字符的索引
//
//        //3.获取方法对象method
//        //谁调用我？我代表谁
//        System.out.println(this);
//
//
//        try {
//            //忽略访问权限修饰符，获取方法
//            Method method = this.getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
//
//           //暴力反射
//            method.setAccessible(true);
//
//            //执行方法
//            method.invoke(this,req,resp);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }


    }
}
