package com.hs.rss.entity;

import com.hs.rss.common.BaseEntity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_rss")
public class RssEntity extends BaseEntity {

    private String title;
    private String link;
    private String description;
}
