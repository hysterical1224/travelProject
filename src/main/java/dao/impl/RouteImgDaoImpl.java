package dao.impl;

import dao.RouteImgDao;
import domain.RouteImg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDs());

    @Override
    public List<RouteImg> findRouteImgs(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jt.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
