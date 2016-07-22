package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;



/**
 * 
 * 租车服务费
 * 
 * @className CarRentalService
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class CarRentalService extends CacalItem<CalculateItem> {

    /**
     * ROUND(C12/C6,2)
     * 
     * C12 服务费
     * C6 期数
     */
    @Override
    public BigDecimal getResult(CalculateItem model, BigDecimal bd1) {

        if (model.getD() == null) {
            throw new RuntimeException(QS_NULL);
        }

        BigDecimal divide = bd1.divide(new BigDecimal(model.getD() + ""), 2, BigDecimal.ROUND_HALF_UP);

        return divide;
    }

}
