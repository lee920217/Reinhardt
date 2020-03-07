package uk.co.cnoversea.api.service;

import uk.co.cnoversea.api.dao.model.News;
import uk.co.cnoversea.web.vo.Page;

public interface INewsService {
    News pub(News record) throws Exception;
    Page<News> page(News page) throws Exception;
    News get(News record) throws Exception;
}
