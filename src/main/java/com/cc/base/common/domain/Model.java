package com.cc.base.common.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public abstract class Model<T extends Model<T>> extends com.baomidou.mybatisplus.extension.activerecord.Model<T> {

}
