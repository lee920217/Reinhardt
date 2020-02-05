package uk.co.cnoversea.api.vo;

import uk.co.cnoversea.web.vo.PageAble;

public class MessageFetchParam extends PageAble {

    private Long tid;
    private Long mid;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }
}
