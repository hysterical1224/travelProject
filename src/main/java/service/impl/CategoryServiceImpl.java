package service.impl;

import dao.impl.CategoryDaoImpl;
import domain.Category;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import service.CategoryService;
import utils.JedisPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDaoImpl cdi = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisPoolUtils.getJedis();
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
//        Set<String> categories = jedis.zrange("category", 0, -1);
        List<Category> cs = null;
        if (categories==null || categories.size()==0){
            System.out.println("在数据库中查找");

            cs = cdi.findAll();
            for (int i=0;i<cs.size();i++){
                jedis.zadd("category", cs.get(i).getCid(),cs.get(i).getCname());

            }


        } else {
            System.out.println("在redis中查找。。。");
            cs = new ArrayList<Category>();
            for (Tuple tuple:categories){
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);

            }

        }


        return cs;
    }
}
