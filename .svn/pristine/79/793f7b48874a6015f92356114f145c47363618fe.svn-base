package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;



/**
 * 
 * 交易服务费
 * 
 * @className JiaoYiServiceFei
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午5:26:42
 */
public class JiaoYiServiceFei extends CacalItem<CalculateItem> {

    /**
     * =ROUND(G15*$C$10,2)
     * 
     * G15 借款余额 C10 融资月利率
     */
    @Override
    public BigDecimal getResult(BigDecimal bd1, BigDecimal bd2) {

        BigDecimal r = bd1.multiply(bd2);

        return r;
    }

}
