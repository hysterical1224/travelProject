package dao.impl;

//import com.alibaba.druid.util.JdbcUtils;
import dao.RouteDao;
import domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDs());

    @Override
    public int findTotalCount(int cid, String search) {
//        String sql = "select count(*) from tab_route where cid = ?";
//        Integer count = jt.queryForObject(sql, Integer.class, cid);
//
//        return count;
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        //
        if (cid>0){
            //有cid参数的时候
            sb.append(" and cid=?");
            params.add(cid);
        }

        if(search!=null && search.length()>0){
            //搜索栏搜素
            sb.append(" and rname like ?");
            params.add("%"+search+"%");
        }


        Integer count = jt.queryForObject(sb.toString(), Integer.class, params.toArray());


        return count;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String search) {
//        String sql = "select * from tab_route where cid = ? limit ?, ?";
//        List<Route> routes = jt.query(sql, new BeanPropertyRowMapper<>(Route.class),cid, start, pageSize);
        String sql = "select * from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        //
        if (cid>0){
            //有cid参数的时候
            sb.append(" and cid= ? ");
            params.add(cid);
        }

        if(search!=null && search.length()>0){
            //搜索栏搜素
            sb.append(" and rname like ?");
            params.add("%"+search+"%");
        }
        sb.append(" limit ?, ?");
        params.add(start);
        params.add(pageSize);


        return jt.query(sb.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = jt.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }
}
