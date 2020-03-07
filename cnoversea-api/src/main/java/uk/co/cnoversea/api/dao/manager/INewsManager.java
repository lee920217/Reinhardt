package uk.co.cnoversea.api.dao.manager;

import uk.co.cnoversea.api.dao.model.News;
import uk.co.cnoversea.web.vo.Page;

public interface INewsManager {
    Page<News> pageQuery(News record);
}
