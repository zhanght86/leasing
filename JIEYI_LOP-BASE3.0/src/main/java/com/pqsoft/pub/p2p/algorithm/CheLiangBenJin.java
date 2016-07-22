package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;

/**
 * 
 * 车辆本金
 * 
 * @className CarRentalService
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class CheLiangBenJin extends CacalItem<CalculateItem> {

    /**
     * F16-E16-D16
     * 
     * F16 月租赁费用
     * E16 交易服务费
     * D16 租车服务费
     */
    @Override
    public BigDecimal getResult(BigDecimal bd1, BigDecimal bd2, BigDecimal bd3) {

        BigDecimal subtract = bd1.subtract(bd2).subtract(bd3);

        return subtract;
    }

}
