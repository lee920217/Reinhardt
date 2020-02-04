package uk.co.cnoversea.web.vo;

/*
 * 这个对象使继承它的对象有可以作为分页参数的能力
 */
public class PageAble extends WebVO {

    private String order_;//排序行
    private String sort_;//正排倒排
    private int pageNum_ = 1;//查询页号 -- 对接前端
    private int pageSize_ = 10;//查询页大小 -- 对接前端
    @SuppressWarnings("unused")
    private int limitStart_;//查询页号 -- 对接mysql
    @SuppressWarnings("unused")
    private int limitSize_;//页大小 -- 对接mysql

    public String getOrder_() {
        return order_;
    }

    public void setOrder_(String order_) {
        this.order_ = order_;
    }

    public String getSort_() {
        return sort_;
    }

    public void setSort_(String sort_) {
        this.sort_ = sort_;
    }

    public int getPageNum_() {
        return pageNum_;
    }

    public void setPageNum_(int pageNum_) {
        this.pageNum_ = pageNum_;
    }

    public int getPageSize_() {
        return pageSize_;
    }

    public void setPageSize_(int pageSize_) {
        this.pageSize_ = pageSize_;
    }

    public int getLimitStart_() {
        this.limitStart_ = (pageNum_ - 1) * pageSize_;
        return this.limitStart_;
    }

    public int getLimitSize_() {
        this.limitSize_ = pageSize_;
        return this.limitSize_;
    }

}
