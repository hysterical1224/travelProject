package service.impl;

import dao.impl.FavoriteDaoImpl;
import domain.Favorite;
import service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDaoImpl fdi = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = fdi.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite!=null;
    }

    @Override
    public int findFavoriteCount(int rid){
        int count = fdi.findCountByRid(rid);
        return count;
    }

    @Override
    public int add(String rid, int uid) {
        fdi.add(Integer.parseInt(rid), uid);
        int count = findFavoriteCount(Integer.parseInt(rid));
        //更新收藏数
        return count;
    }
}
