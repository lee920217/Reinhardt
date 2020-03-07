package uk.co.cnoversea.api.service;

import uk.co.cnoversea.api.dao.model.User;

public interface IUserService {
    User regist(User user) throws Exception;
    User active(User user) throws Exception;
    boolean login(User user) throws Exception;
}
