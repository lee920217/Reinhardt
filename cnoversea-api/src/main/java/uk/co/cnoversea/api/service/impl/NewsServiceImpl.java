package uk.co.cnoversea.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.manager.INewsManager;
import uk.co.cnoversea.api.dao.mapper.NewsMapper;
import uk.co.cnoversea.api.dao.model.News;
import uk.co.cnoversea.api.service.INewsService;
import uk.co.cnoversea.web.vo.Page;

@Service
public class NewsServiceImpl implements INewsService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private INewsManager newsManager;

    @Override
    public News pub(News news) throws Exception {
        news.setNid(null);
        news.setCreateTime(null);
        news.setModifyTime(null);

        if(newsMapper.insertSelective(news) == 1){
            return news;
        }
        return null;
    }

    @Override
    public Page<News> page(News record) throws Exception {
        return newsManager.pageQuery(record);
    }

    @Override
    public News get(News record) throws Exception {
        return newsMapper.selectByPrimaryKey(record.getNid());
    }

}
