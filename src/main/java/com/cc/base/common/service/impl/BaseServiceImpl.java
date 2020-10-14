package com.cc.base.common.service.impl;

import com.cc.base.common.domain.BaseModel;
import com.cc.base.common.mapper.BaseMapper;
import com.cc.base.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 接口实现基类
 *
 * @param <M>
 * @param <T>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements BaseService<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


}
