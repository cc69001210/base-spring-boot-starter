package com.cc.base.common.service.impl;


import com.cc.base.common.domain.Model;
import com.cc.base.common.mapper.BaseMapper;
import com.cc.base.common.service.IService;

/**
 * @param <M>
 * @param <T>
 */
public abstract class ServiceImpl<M extends BaseMapper<T>, T extends Model> extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M, T> implements IService<T> {

    protected static final Integer DEFAULT_PARENT_ID = 0;
}
