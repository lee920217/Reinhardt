package uk.co.cnoversea.api.dao.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.manager.INewsManager;
import uk.co.cnoversea.api.dao.mapper.NewsMapper;
import uk.co.cnoversea.api.dao.model.News;
import uk.co.cnoversea.web.vo.Page;

import java.util.List;

@Service
public class NewsManagerImpl implements INewsManager {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Page<News> pageQuery(News record) {
        List<News> list = newsMapper.selectPage(record);
        Page<News> page = new Page<News>();
        page.setRows(list);
        page.setRecords(newsMapper.pageCount(record));
        page.setPageParameter(record);
        page.pageCalculation();
        return page;
    }

}
