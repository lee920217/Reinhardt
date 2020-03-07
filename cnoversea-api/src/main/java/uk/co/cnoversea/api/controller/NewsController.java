package uk.co.cnoversea.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.cnoversea.api.dao.model.News;
import uk.co.cnoversea.api.service.INewsService;
import uk.co.cnoversea.web.controller.AbstractController;
import uk.co.cnoversea.web.vo.Page;
import uk.co.cnoversea.web.vo.RequestVO;
import uk.co.cnoversea.web.vo.ResponseVO;

@RestController
@RequestMapping("/news")
public class NewsController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private INewsService newsService;

    //发布新闻
    @RequestMapping(value = "/pub", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<News> pub(@RequestBody RequestVO<News> param) {
        News task = param.getQuery();
        News taskRet = null;
        try {
            taskRet = newsService.pub(task);
            return genResponse(taskRet);
        } catch (Exception e) {
            logger.error("pub news fail", e);
            return genResponse(ResponseVO.CODE_ERR, "pub news fail, " + e.getMessage() , task);
        }
    }

    //分页查询新闻
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<Page<News>> page(@RequestBody RequestVO<News> param) {
        News task = param.getQuery();
        Page<News> page = null;
        try {
            page = newsService.page(task);
            return genResponse(page);
        } catch (Exception e) {
            logger.error("page query news fail", e);
            return genResponse(ResponseVO.CODE_ERR, "page query news fail, " + e.getMessage() , page);
        }
    }

    //分页查询新闻
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<News> get(@RequestBody RequestVO<News> param) {
        News news = param.getQuery();
        try {
            return genResponse(newsService.get(news));
        } catch (Exception e) {
            logger.error("get news fail", e);
            return genResponse(ResponseVO.CODE_ERR, "get news fail, " + e.getMessage() , news);
        }
    }
}
