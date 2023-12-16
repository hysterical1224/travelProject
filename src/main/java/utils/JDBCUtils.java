package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource ds;

    static {

        try {
            Properties pro = new Properties();
            InputStream rs = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            pro.load(rs);
//            初始化连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {


        }
    }

    public static DataSource getDs(){
        return ds;
    }

    //获取连接Connection对象
    public  static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }





}
