package uk.co.cnoversea.web.vo;

import java.util.List;

/*
 * 这个对象封装的是分页查询后返回的一页数据
 */
public class Page<T> extends WebVO {

    private PageAble pageParameter;
    private long total;// 总页数
    private long page;// 当前页
    private long records;// 查询出的记录数
    private List<T> rows;// 当前页数据

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public PageAble getPageParameter() {
        return pageParameter;
    }

    public void setPageParameter(PageAble pageParameter) {
        this.pageParameter = pageParameter;
    }

    public void pageCalculation() {
        this.page = this.pageParameter.getPageNum_();
        this.total = this.records / this.pageParameter.getPageSize_() + (this.records % this.pageParameter.getPageSize_() == 0 ? 0 : 1);
    }
}
