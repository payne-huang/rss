package com.hs.rss.common;

import java.util.Date;
import java.util.UUID;

public class BaseServiceImpl {

    protected void setCreateBaseEntity(BaseEntity baseEntity) {
        Date date = new Date();
        baseEntity.setCreateTime(date);
        baseEntity.setUuid(UUID.randomUUID().toString());
    }
}
