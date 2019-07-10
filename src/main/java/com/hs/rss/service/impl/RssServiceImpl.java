package com.hs.rss.service.impl;

import com.hs.rss.common.BaseServiceImpl;
import com.hs.rss.entity.RssEntity;
import com.hs.rss.mapper.RssMapper;
import com.hs.rss.service.AriaService;
import com.hs.rss.service.RssService;
import com.hs.rss.vo.CreateRssVO;
import com.rsslibj.elements.Channel;
import com.rsslibj.elements.Item;
import com.rsslibj.elements.RSSReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RssServiceImpl extends BaseServiceImpl implements RssService {

    @Value("${rss.urls}")
    private String rssUrls;

    public static List<String> tvList = new ArrayList<>();

    @Autowired
    RssMapper rssMapper;

    @Autowired
    AriaService ariaService;

    @Override
    public RssEntity add(CreateRssVO createRssVO) {
        RssEntity rssEntity = new RssEntity();
        BeanUtils.copyProperties(createRssVO,rssEntity);
        setCreateBaseEntity(rssEntity);
        rssMapper.insert(rssEntity);
        return rssEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<CreateRssVO> createRssVOS) {
        List<RssEntity> rssEntities = rssMapper.selectAll();
        createRssVOS.forEach(createRssVO -> {
            if (!isExist(createRssVO, rssEntities)) {
                this.add(createRssVO);
                ariaService.addAriaTask(createRssVO.getLink());
            }
        });
    }

    private boolean isExist(CreateRssVO createRssVO, List<RssEntity> rssEntities) {
        String link1 = createRssVO.getLink();
        for (RssEntity rssEntity : rssEntities) {
            String link2 = rssEntity.getLink();
            if (StringUtils.equalsIgnoreCase(getMag(link1),getMag(link2))||StringUtils.equalsIgnoreCase(rssEntity.getTitle(), createRssVO.getTitle())) {
                return true;
            }
        }
        return false;
    }

    private String getMag(String link) {
        String mag = "";
        String[] links = link.split("&");
        if (links.length > 0) {
            mag = links[0];
        }
        return mag;
    }

    @Override
    public void rss() {
        String[] rssUrlArray = rssUrls.split(";");
        for (String rssUrl : rssUrlArray) {
            try {
                List<CreateRssVO> createRssVOS = readerRSS(rssUrl);
                batchAdd(createRssVOS);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("get rss info fail!");
            }
        }
    }

    private List<CreateRssVO> readerRSS(String rssUrl) throws Exception {
        URL url = new URL(rssUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream in = conn.getInputStream();
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        RSSReader rssReader = new RSSReader(bufReader);

        Channel channel = rssReader.getChannel();
        List<Item> itemList = channel.getItems();
        List<CreateRssVO> createRssVOS = new ArrayList<>();
        if (itemList != null) {
            for (Item item : itemList) {
                String title = item.getTitle();
                if (filter(title)) {
                    String link = item.getLink();
                    String description = item.getDescription();
                    CreateRssVO createRssVO = new CreateRssVO();
                    createRssVO.setLink(link);
                    createRssVO.setDescription(description);
                    createRssVO.setTitle(title);
                    createRssVOS.add(createRssVO);
                }
            }
        }
        bufReader.close();
        in.close();
        return createRssVOS;
    }

    private boolean filter(String title) {
        if (!title.matches(".*([S,s][0-9]+)?[E,e]([P,p])?[0-9]+.*")
                && !title.matches(".*第[一,二,三,四,五,六,七,八,九,十,0-9]+[季,集].*")) {
            return true;
        }
        for (String tv: tvList) {
            if (title.contains(tv)){
                return true;
            }
        }
        return false;
    }
}
