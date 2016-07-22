package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;

import com.pqsoft.pub.p2p.algorithm.algorithminterface.CacalItem;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;



/**
 * 
 * 服务费
 * 
 * @className ServiceCharge
 * @author 刘丽
 * @version V1.0 2016年4月13日 下午12:44:14
 */
public class ServiceCharge extends CacalItem<CalculateItem> {

    /**
     * C11-融资额
     * 总合同金额
     */
    @Override
    public BigDecimal getResult(CalculateItem model, BigDecimal bd1) {

        if (model.getA() == null) {
            throw new RuntimeException(RZE_NULL);
        }

        BigDecimal r = bd1.subtract(model.getA());
        
    	//判断是否按比例计算  并且yes 
    	if(model.getIsRatioCalculate().getValue()==1){//如果是yes,计算额外费用
    		if(model.getAdditionalCosts() == null){
    			 throw new RuntimeException(RWFY_NULL);
    		}else{
    			r = r.add(model.getAdditionalCosts());
    		}
    	}

        r = r.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);
        return r;
    }

}
