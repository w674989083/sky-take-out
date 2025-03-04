package com.sky.result;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.utils.BeanUtils;
import com.sky.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    protected Long total;
    protected Long pages;
    protected List<T> records;

    public static <T> PageResult<T> empty(Long total, Long pages) {
        return new PageResult<>(total, pages, Collections.emptyList());
    }
    public static <T> PageResult<T> empty(Page<?> page) {
        return new PageResult<>(page.getTotal(), page.getPages(), Collections.emptyList());
    }

    public static <T> PageResult<T> of(Page<T> page) {
        if(page == null){
            return new PageResult<>();
        }
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageResult<>(page.getTotal(), page.getPages(), page.getRecords());
    }
    public static <T,R> PageResult<T> of(Page<R> page, Function<R, T> mapper) {
        if(page == null){
            return new PageResult<>();
        }
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.getRecords().stream().map(mapper).collect(Collectors.toList()));
    }
    public static <T> PageResult<T> of(Page<?> page, List<T> list) {
        return new PageResult<>(page.getTotal(), page.getPages(), list);
    }

    public static <T, R> PageResult<T> of(Page<R> page, Class<T> clazz) {
        return new PageResult<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getRecords(), clazz));
    }

    public static <T, R> PageResult<T> of(Page<R> page, Class<T> clazz, Convert<R, T> convert) {
        return new PageResult<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getRecords(), clazz, convert));
    }
}
