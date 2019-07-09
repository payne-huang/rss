package com.hs.rss.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.hs.rss.common.BaseServiceImpl;
import com.hs.rss.service.AriaService;
import com.hs.rss.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class AriaServiceImpl extends BaseServiceImpl implements AriaService {

    @Autowired
    RssService rssService;

    JsonRpcHttpClient client;

    private String token = "huangsheng";

    @Value("${aria2.url}")
    String aria2Url;

    @Override
    public String addAriaTask(String magnet) {
        return addDownloadTask(magnet);
    }

    private String execute(String methodName, Object[] param) {
        try {
            client = new JsonRpcHttpClient(new URL(aria2Url));
            return client.invoke(methodName, new Object[]{"token:" + token, param}, String.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private String addDownloadTask(String magnet) {
        Object[] uris = new Object[]{magnet};
        return execute("aria2.addUri", uris);
    }

}
