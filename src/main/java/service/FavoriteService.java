package service;

public interface FavoriteService {
    public boolean isFavorite(String rid, int uid);

    public int findFavoriteCount(int rid);

    //添加收藏
    public int add(String rid, int uid);
}
