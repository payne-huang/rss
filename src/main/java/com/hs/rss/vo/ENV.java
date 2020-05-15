package com.hs.rss.vo;

import org.apache.commons.lang3.StringUtils;

public class ENV {
    public static String RSS_HOST;

    public ENV(){
        RSS_HOST = System.getenv("RSS_HOST");
        if (StringUtils.isBlank(RSS_HOST)) {
            RSS_HOST = "http://mag234.com/index/rss?type=magnet&cid=25";
        }
    }
}
