package com.hs.rss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hs.rss.service.RssService;
import com.hs.rss.service.impl.RssServiceImpl;
import com.hs.rss.service.impl.StaticScheduleTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sheng.huang
 */
@Api(description = "订阅相关接口")
@RestController
public class RssController {

    @Autowired
    RssService rssService;

    @ApiOperation(value = "添加可订阅视频名字")
    @GetMapping("/tv/{tv}/add")
    public JSONObject tvAdd(@PathVariable String tv) {
        RssServiceImpl.tvList.add(tv);
        return JSONObject.parseObject("{\"status\":\"ok\"}");
    }

    @ApiOperation(value = "移除订阅视频")
    @GetMapping("/tv/{index}/del")
    public JSONObject tvDel(@PathVariable int index) {
        RssServiceImpl.tvList.remove(index - 1);
        return JSONObject.parseObject("{\"status\":\"ok\"}");
    }

    @ApiOperation(value = "订阅的枚举列表")
    @GetMapping("/tv/list")
    public JSONObject tvList() {
        Map<Integer, String> tvList = new HashMap<>();
        for (int i = 0; i < RssServiceImpl.tvList.size(); i++) {
            tvList.put(i + 1, RssServiceImpl.tvList.get(i));
        }
        return JSONObject.parseObject(JSON.toJSONString(tvList));
    }

    @ApiOperation("定时任务开关设置")
    @GetMapping("/switch")
    public JSONObject switch_on() {
        StaticScheduleTask.TASK_SWITCH = !StaticScheduleTask.TASK_SWITCH;
        return JSONObject.parseObject("{\"status\":\"ok\",\"switch\":" + StaticScheduleTask.TASK_SWITCH + "}");
    }
}
