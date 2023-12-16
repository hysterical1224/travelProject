package domain;

import java.util.List;
import java.util.Objects;

public class PageBean<T> {
    private int totalCount;//总记录数
    private int totalPage;//总页数
    private int currentPage;//当前页码
    private int pageSize;//每页显示的条数
    private List<T> list;//每页显示的数据集合

    /**
     * 可以根据设备类型返回适当的每页数据量。
     * */

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageBean)) return false;
        PageBean<?> pageBean = (PageBean<?>) o;
        return totalCount == pageBean.totalCount && totalPage == pageBean.totalPage && currentPage == pageBean.currentPage && pageSize == pageBean.pageSize && Objects.equals(list, pageBean.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, totalPage, currentPage, pageSize, list);
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
