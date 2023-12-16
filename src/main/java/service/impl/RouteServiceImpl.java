package service.impl;

import dao.RouteDao;
import dao.RouteImgDao;
import dao.SellerDao;
import dao.impl.RouteDaoImpl;
import dao.impl.RouteImgDaoImpl;
import dao.impl.SellerDaoImpl;
import domain.PageBean;
import domain.Route;
import domain.RouteImg;
import domain.Seller;
import service.FavoriteService;
import service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao rd = new RouteDaoImpl();
    RouteImgDao routeImgDao = new RouteImgDaoImpl();
    SellerDao sd = new SellerDaoImpl();
    FavoriteService fs = new FavoriteServiceImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String search) {
        //总记录数,总页数,当前页码,每页显示的条数,每页显示的数据集合
        PageBean<Route> pb = new PageBean<>();

        //当前页码
        pb.setCurrentPage(currentPage);

        //每页显示的条数
        pb.setPageSize(pageSize);


        //总记录数需要从数据库中查找
        int totalCount = rd.findTotalCount(cid, search);
        pb.setTotalCount(totalCount);


        //查找当前页显示的数据集合，start=每页页数*（当前页-1）
        int start = pageSize*(currentPage-1);
        pb.setList(rd.findByPage(cid, start, pageSize, search));

        //设置总页数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        pb.setTotalPage(totalPage);



        return pb;
    }

    @Override
    public Route findOne(int rid) {
        //1.根据id去route表中查询route对象
        Route route = rd.findOne(rid);

        //2.根据route的id查询图片集合信息
        List<RouteImg> routeImgs = routeImgDao.findRouteImgs(rid);
        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgs);

        //3.根据route的sid查询商家对象
        int sid = route.getSid();
        Seller seller = sd.findSeller(sid);
        route.setSeller(seller);

        int favoriteCount = fs.findFavoriteCount(rid);
        route.setCount(favoriteCount);


        return route;
    }

}
