package dao;

import domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    public List<RouteImg> findRouteImgs(int rid);
}
