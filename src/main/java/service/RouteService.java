package service;

import domain.PageBean;
import domain.Route;

public interface RouteService {

    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String search);

    public Route findOne(int rid);
}
