package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisPoolUtils {
    private static JedisPool jedisPool;

    //配置文件
    static {
        //读取配置文件
        InputStream rs = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        Properties properties = new Properties();
        try {
            properties.load(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关联数据
        JedisPoolConfig jpc = new JedisPoolConfig();


        jpc.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        jpc.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));

        //初始化jedis
        jedisPool = new JedisPool(jpc, properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));



    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
