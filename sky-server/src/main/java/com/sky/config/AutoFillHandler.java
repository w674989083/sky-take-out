package com.sky.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器,实现零侵入自动注入
 */
@Component
public class AutoFillHandler implements MetaObjectHandler {

    private static final Logger log = LoggerFactory.getLogger(AutoFillHandler.class);

    // 获取当前用户（需结合具体权限框架）
    private Long getCurrentUser() {
        return BaseContext.getCurrentId();
    }


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, AutoFillConstant.CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, AutoFillConstant.UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, AutoFillConstant.CREATE_USER, this::getCurrentUser, Long.class);
        this.strictInsertFill(metaObject, AutoFillConstant.UPDATE_USER, this::getCurrentUser, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, AutoFillConstant.UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, AutoFillConstant.UPDATE_USER, this::getCurrentUser, Long.class);
    }
}
