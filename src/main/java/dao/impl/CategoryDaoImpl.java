package dao.impl;

import dao.CategoryDao;
import domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import javax.sql.rowset.JdbcRowSet;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDs());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return jt.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
