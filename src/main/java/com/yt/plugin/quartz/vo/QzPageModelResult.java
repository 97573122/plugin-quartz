package com.yt.plugin.quartz.vo;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 统一分页接口返回对象
 * @author zhuzhengjie
 */
public class QzPageModelResult<T> extends QzResp {

    /**
     * 当前页
     */
    private int currentPage = 1;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总记录数
     */
    private Long totalCount;

    private List<T> model ;

    public QzPageModelResult(Page<T> pager) {
        super() ;
        this.setPageSize(pager.getPageSize()) ;
        this.setCurrentPage(pager.getPageNum());
        this.setTotalPage(pager.getPages());
        this.setTotalCount(pager.getTotal());
        this.setModel(pager.getResult());
    }

    public QzPageModelResult(){
        super() ;
    }

    public List<T> getModel() {
        return model;
    }

    public void setModel(List<T> model) {
        this.model = model;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "PageModelResult [curPage=" + currentPage + ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", totalCount=" + totalCount + ", toString()=" + super.toString() + "]";
    }
}
