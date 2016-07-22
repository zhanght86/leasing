package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;



/**
 * 
 * 融资月利率
 * 
 * @className FinancingMonthInterestRate
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class FinancingMonthInterestRate extends CacalItem<CalculateItem> {

    /**
     * ROUND(L4/12,8)
     * 
     * L4 内部利率
     */
    @Override
    public BigDecimal getResult(CalculateItem model) {

        if (model.getNblv() == null) {
            throw new RuntimeException(NBLV_NULL);
        }
        if (model.getNblv().doubleValue() == 0) {
            throw new RuntimeException(NBLV_0);
        }

        BigDecimal result = model.getNblv().divide(new BigDecimal("12"), 8, BigDecimal.ROUND_HALF_UP);

        return result;
    }
}
