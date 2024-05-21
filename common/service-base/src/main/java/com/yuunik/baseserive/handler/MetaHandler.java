package com.yuunik.baseserive.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MetaHandler implements MetaObjectHandler {

    // 插入时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填充创建时间
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        // 自动填充更新时间
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    // 更新时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充更新时间
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
