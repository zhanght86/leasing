package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;



/**
 * 
 * 借款余额
 * 
 * @className CarRentalService
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class JieKuanYuE extends CacalItem<CalculateItem> {

    /**
     * G15-C16-D16
     * 
     * C15 上一期的借款余额
     * C16 本期的车辆本金
     * D16 本期的租车服务费
     */
    @Override
    public BigDecimal getResult(BigDecimal bd1, BigDecimal bd2, BigDecimal bd3) {

        BigDecimal subtract = bd1.subtract(bd2).subtract(bd3);

        return subtract;
    }

}
