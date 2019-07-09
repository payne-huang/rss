package com.hs.rss.controller;

import com.hs.rss.service.RssService;
import com.hs.rss.service.impl.RssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    RssService rssService;

    @RequestMapping("/tv/{tv}/add")
    public String tvAdd(@PathVariable String tv) {
        RssServiceImpl.tvList.add(tv);
        return "ok";
    }

    @RequestMapping("/tv/{index}/del")
    public String tvDel(@PathVariable int index) {
        RssServiceImpl.tvList.remove(index - 1);
        return "ok";
    }

    @RequestMapping("/tv/list")
    public String tvList() {
        Map<Integer, String> tvList = new HashMap<>();
        for (int i = 0; i < RssServiceImpl.tvList.size(); i++) {
            tvList.put(i+1, RssServiceImpl.tvList.get(i));
        }
        return String.valueOf(tvList);
    }

}
