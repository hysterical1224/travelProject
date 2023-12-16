package dao.impl;

import dao.FavoriteDao;
import domain.Favorite;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDs());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        favorite = jt.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class),rid,uid);

        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select COUNT(*) from tab_favorite where rid = ?";

        return jt.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        jt.update(sql,rid,new Date(),uid);
    }
}
