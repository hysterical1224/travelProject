package dao;

import domain.Favorite;

public interface FavoriteDao {

    public Favorite findByRidAndUid(int rid, int uid);


    public int findCountByRid(int rid);

    public void add(int parseInt, int uid);
}
