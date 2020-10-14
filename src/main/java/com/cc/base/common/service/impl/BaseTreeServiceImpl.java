package com.cc.base.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.base.common.domain.BaseTreeModel;
import com.cc.base.common.mapper.BaseTreeMapper;
import com.cc.base.common.service.BaseTreeService;
import com.cc.base.enums.FlagEnum;
import com.cc.base.exception.ValidateException;
import io.swagger.models.auth.In;
import org.springframework.util.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * tree接口实现基类
 *
 * @param <M>
 * @param <T>
 */
public class BaseTreeServiceImpl<M extends BaseTreeMapper<T>, T extends BaseTreeModel> extends BaseServiceImpl<M, T> implements BaseTreeService<T> {


    /**
     * <p>
     * 插入一条记录 需更新父节点信息
     * </p>
     */
    @Override
    public synchronized boolean save(T entity) {
        saveParentLeafFlag(entity);
        entity.setLeafFlag(FlagEnum.YES.getCode());
        return super.save(entity);
    }

    /**
     * 设置父节点ID
     *
     * @param entity
     */
    public void setParentId(T entity) {
        Integer parentId = entity.getParentId();
        if (parentId == null) {
            parentId = DEFAULT_PARENT_ID;
        }
        entity.setParentId(parentId);
    }

    /**
     * 新增修改父节点
     *
     * @param entity
     */
    private synchronized void saveParentLeafFlag(T entity) {
        this.setParentId(entity);
        if (!"0".equals(entity.getParentId())) {
            T parentEntity = this.getById(entity.getParentId());
            parentEntity.setLeafFlag(FlagEnum.NO.getCode());
            super.updateById(parentEntity);
        }
    }

    /**
     * 更新叶子节点
     *
     * @param entity
     */
    private synchronized void updateParentLeafFlag(T entity) {
        setParentId(entity);
        //如果父节点不为空 更新父节点叶子节点标识
        if (!"0".equals(entity.getParentId())) {
            T parentEntity = this.getById(entity.getParentId());
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", parentEntity.getId());
            if (this.count(queryWrapper) > 0) {
                parentEntity.setLeafFlag(FlagEnum.NO.getCode());
            } else {
                parentEntity.setLeafFlag(FlagEnum.YES.getCode());
            }
            super.updateById(parentEntity);
        }
    }

    /**
     * <p>
     * 更新记录 需判断父节点是否改变
     * </p>
     */
    @Override
    public synchronized boolean updateById(T entity) {
        boolean flag = true;
        this.setParentId(entity);
        if (entity.getId().equals(entity.getParentId())) {
            throw new ValidateException("该节点的上级节点不能是其本身！");
        }
        //判断上级节点是否为当前节点的子节点
        if (entity.getParentId() == null || entity.getParentId() == DEFAULT_PARENT_ID) {
            List<Integer> childrenPkList = this.getChildrenPk(entity.getId());
            if (childrenPkList.contains(entity.getParentId())) {
                throw new ValidateException("该节点的上级节点不能是该节点的子节点！");
            }
        }
        T oldEntity = this.getById(entity.getId());
        if (oldEntity != null) {
            if (oldEntity.getParentId() != (entity.getId())) {
                saveParentLeafFlag(entity);
            }
            if (flag = super.updateById(entity)) {
                updateParentLeafFlag(oldEntity);
            }
        }
        return flag;
    }


    /**
     * <p>
     * 根据 ID 删除 需更新父节点信息
     * </p>
     */
    @Override
    public synchronized boolean removeById(Serializable id) {
        T entity = this.getById(id);
        boolean flag = true;
        if (entity != null) {
            if (flag = super.removeById(id)) {
                updateParentLeafFlag(entity);
            }
        }
        return flag;
    }

    @Override
    public List<Integer> getChildrenPk(Serializable id) {
        List<Integer> childAllList = new ArrayList<>();
        List<BaseTreeModel> childList = queryList(id);
        getListChild(childAllList, childList);
        return childAllList;
    }

    @Override
    public List<Integer> getParentPk(Serializable id) {
        List<Integer> parentAllList = new ArrayList<>();
        T entity = this.getById(id);
        if (entity != null && entity.getParentId() != DEFAULT_PARENT_ID) {
            getListParent(parentAllList, entity.getParentId());
        }
        return parentAllList;
    }

    @Override
    public List<T> selectChild(Serializable parentId) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (parentId == null) {
            parentId = DEFAULT_PARENT_ID;
        }
        lambdaQueryWrapper.eq(T::getParentId, parentId);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public int selectChildCount(Serializable parentId) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (parentId == null) {
            parentId = DEFAULT_PARENT_ID;
        }
        lambdaQueryWrapper.eq(T::getParentId, parentId);
        return this.count(lambdaQueryWrapper);
    }

    /**
     * 递归获取所有上级ID
     *
     * @param parentAllList
     * @param parentAllList
     */
    private void getListParent(List<Integer> parentAllList, Serializable parentId) {
        T parentEntity = this.getById(parentId);
        if (parentEntity != null && parentEntity.getParentId() != null) {
            parentAllList.add(parentEntity.getId());
            if (parentEntity.getParentId() != DEFAULT_PARENT_ID) {
                getListParent(parentAllList, parentEntity.getParentId());
            } else {
                return;
            }
        } else {
            return;
        }
    }

    /**
     * 递归获取所有下级ID
     *
     * @param childAllList
     * @param childList
     */
    private void getListChild(List<Integer> childAllList, List<BaseTreeModel> childList) {
        for (BaseTreeModel model : childList) {
            List<BaseTreeModel> list = queryList(model.getId());
            if (list != null && list.size() > 0) {
                getListChild(childAllList, list);
            }
            childAllList.add(model.getId());
        }
    }

    private List<BaseTreeModel> queryList(Serializable id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in("parent_id", id);
        wrapper.select("id");
        return (List<BaseTreeModel>) this.list(wrapper);
    }
}
