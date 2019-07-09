package com.hs.rss.service;

import com.hs.rss.entity.RssEntity;
import com.hs.rss.vo.CreateRssVO;

import java.util.List;

public interface RssService {

    RssEntity add(CreateRssVO createRssVO);

    void batchAdd(List<CreateRssVO> createRssVOS);

    void rss();
}
