package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;


/**
 * 
 * 月租赁费用
 * 
 * @className MonthRentalExpense
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class MonthRentalExpense extends CacalItem<CalculateItem> {

    /**
     * ROUND(融资额/C6,2)+ROUND(融资额*C5,2)
     * 
     * C6 租赁期限
     * 
     * C5 月综合费用率
     * 
     */
    @Override
    public BigDecimal getResult(CalculateItem model) {

        if (model.getA() == null) {
            throw new RuntimeException(RZE_NULL);
        }
        if (model.getD() == null) {
            throw new RuntimeException(QS_NULL);
        }
        // if (model.getMonthComprehensiveRate() == null) {
        // throw new RuntimeException(MONTHCOMPREHENSIVERATE_NULL);
        // }

        if (model.getC() == null) {
            throw new RuntimeException(NLV_NULL);
        }
        if (model.getC().doubleValue() == 0) {
            throw new RuntimeException(NLV_0);
        }

        if (model.getCarryType() == null) {
            throw new RuntimeException(JWFS_NULL);
        }

        // 月综合费用率

        BigDecimal divide = model.getC().divide(new BigDecimal("12"), 6, BigDecimal.ROUND_HALF_UP);

        BigDecimal r1 = model.getA().divide(new BigDecimal(model.getD() + ""), 6, BigDecimal.ROUND_HALF_UP);// ROUND(融资额/C6,2)
        BigDecimal r2 = model.getA().multiply(divide);// model.getMonthComprehensiveRate()); // ROUND(融资额*C5,2)

        BigDecimal r3 = null;// (r1.add(r2)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);

        if (model.getCarryType().getValue() == 2) {// 进位方式四舍五入
            if (2 == model.getDigitType().getValue()) {// 两位小数
                r3 = (r1.add(r2)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);
            } else if (4 == model.getDigitType().getValue()) {// 取整
                r3 = (r1.add(r2)).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_HALF_UP);
                r3 = r3.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP );
            } else {
                throw new RuntimeException(JD_VALUE);
            }
        } else if (model.getCarryType().getValue() == 1) {// 直接进位
            if (4 == model.getDigitType().getValue()) {// 取整
                r3 = (r1.add(r2)).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP);
                r3 = r3.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP );
            }
            // else if (4 == model.getDigitType().getValue()) {// 十位
            //
            // } else if (4 == model.getDigitType().getValue()) {// 百位
            //
            // }
            else {
                throw new RuntimeException(JD_VALUE);
            }
        } else {
            throw new RuntimeException(JWFS_ERROR);
        }
        
        return r3;

    }
}
