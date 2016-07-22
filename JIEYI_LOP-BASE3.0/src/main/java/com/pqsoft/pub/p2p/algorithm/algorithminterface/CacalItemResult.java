package com.pqsoft.pub.p2p.algorithm.algorithminterface;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;


/**
 * 
 * 计算项结果
 * 
 * @className CacalItemResult
 * @author 刘丽
 * @version V1.0 2016年3月31日 上午8:45:17
 */
public abstract class CacalItemResult<T extends CalculateItem> {

    /**
     * 子类需要重写,返回具体的实现结果
     *
     * @author 刘丽
     * @since 2016年3月30日17:36:34
     * @param model 表达式每项的值
     * @return BigDecimal 返回结果
     */
    public BigDecimal getResult(T model) {
        return null;
    }

    public BigDecimal getResult(BigDecimal bd1, BigDecimal bd2) {
        return null;
    }

    public BigDecimal getResult(BigDecimal bd1, BigDecimal bd2, BigDecimal bd3) {
        return null;
    }

    public BigDecimal getResult(T model, BigDecimal bd1) {
        return null;
    }

    public BigDecimal getResult(T model, BigDecimal bd1, BigDecimal bd2) {
        return null;
    }

    public BigDecimal getResult(T model, BigDecimal bd1, BigDecimal bd2, BigDecimal bd3) {
        return null;
    }
}
