package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;



/**
 * 
 * 总合同金额
 * 
 * @className TotalContractAmount
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class TotalContractAmount extends CacalItem<CalculateItem> {

    /**
     * 
     * ROUND(ROUND(C9/C10,8)*(1-ROUND(1/(1+C10)^C6,8)),2)
     * 
     * C9/C10*(1-1/(1+C10)^C6)
     * 月租赁费用 /融资月利率*(1-1/(1+融资月利率)^租赁期限)
     * 
     * bd1 月租赁费用
     * 
     * bd2 融资月利率
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.jiezh.pub.algorithm.algorithminterface.CacalItemResult#getResult(com.jiezh.pub.algorithm.model.CalculateItem, java.math.BigDecimal,
     * java.math.BigDecimal)
     */
    @Override
    public BigDecimal getResult(CalculateItem model, BigDecimal bd1, BigDecimal bd2) {

        if (model.getD() == null) {
            throw new RuntimeException(QS_NULL);
        }

        BigDecimal value = new BigDecimal("1").add(bd2); // (1+融资月利率)

        BigDecimal pow = value.pow(model.getD());// (1+融资月利率)^租赁期限

        BigDecimal divide = new BigDecimal("1").divide(new BigDecimal(pow + "").divide(new BigDecimal("1"), 8, BigDecimal.ROUND_HALF_UP), 15,
            BigDecimal.ROUND_HALF_UP);// 1/(1+融资月利率)^租赁期限

        BigDecimal subtract = new BigDecimal("1").subtract(divide);// (1-1/(1+融资月利率)^租赁期限)

        BigDecimal divide2 = bd1.divide(bd2, 8, BigDecimal.ROUND_HALF_UP).multiply(subtract);

        BigDecimal divide3 = divide2.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);

        return divide3;
    }

}
