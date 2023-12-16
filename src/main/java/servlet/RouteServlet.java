package servlet;

import domain.PageBean;
import domain.Route;
import domain.User;
import service.RouteService;
import service.impl.FavoriteServiceImpl;
import service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RouteServlet", value = "/route/*")
public class RouteServlet extends BaseServlet {
    RouteService rs = new RouteServiceImpl();
    FavoriteServiceImpl fsi = new FavoriteServiceImpl();
    protected void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RouteSevlet的pageQuery被执行了。。。");
        //接受参数:当前页面，score（cid），页面数据大小
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String search = request.getParameter("search");

        //鲁棒性处理，将一些非正常数据或者初始化页都归到第1页
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        } else{
            currentPage = 1;
        }

        //redis中score的排序
        int cid = 0;
        if (cidStr != null && cidStr.length()>0 && !"null".equals(cid)){
            cid = Integer.parseInt(cidStr);
        } else{
            cid = 1;
        }

        //页面大小
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        //调用service查询pageBean对象


        PageBean<Route> routePageBean = rs.pageQuery(cid, currentPage, pageSize, search);
        System.out.println(routePageBean);



        //将pageBean对象序列化为json，返回
        writeValue(routePageBean,response);



    }

    protected void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.接收id
        String ridStr = request.getParameter("rid");
        int rid;
        if (ridStr != null){
            rid = Integer.parseInt(ridStr);
        } else {
            rid = 0;
        }
        //2.调用service查询route对象
        Route route = rs.findOne(rid);

        //3.转化为json，写到客户端上
        writeValue(route,response);
    }

    protected void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取路线id
        String rid = request.getParameter("rid");

        //获取当前登陆状态
        User user = (User)request.getSession().getAttribute("user");

        int uid;
        if (user == null){
            //用户没有登陆
            uid=0;
        } else {
            //用户已经登陆
            uid = user.getUid();
        }

        //调用FavoriteService
        boolean favorite = fsi.isFavorite(rid, uid);
        System.out.println("收藏了："+favorite);

        writeValue(favorite,response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        //路线
        //获取当前登陆的用户
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            //用户尚未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //调用service添加
        int counts = fsi.add(rid, uid);
        //更新收藏数


    }
}
