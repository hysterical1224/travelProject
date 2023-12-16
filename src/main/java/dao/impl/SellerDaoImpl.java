package dao.impl;

import dao.SellerDao;
import domain.Seller;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
    JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDs());

    @Override
    public Seller findSeller(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller seller = jt.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }
}
