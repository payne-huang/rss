package com.hs.rss.mapper;

import com.hs.rss.entity.RssEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RssMapper {
    @Insert("INSERT INTO t_rss(uuid, title, link, description, createTime) VALUES(#{uuid}, #{title}, #{link}, "
            + "#{description}, #{createTime})")
    void insert(RssEntity rssEntity);

    @Select("SELECT * FROM t_rss")
    List<RssEntity> selectAll();
}
