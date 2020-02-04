package uk.co.cnoversea.web.vo;

public class RequestVO<T extends WebVO> extends WebVO {
    private T query;
    private AuthVO auth;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }

    public AuthVO getAuth() {
        return auth;
    }

    public void setAuth(AuthVO auth) {
        this.auth = auth;
    }
}
