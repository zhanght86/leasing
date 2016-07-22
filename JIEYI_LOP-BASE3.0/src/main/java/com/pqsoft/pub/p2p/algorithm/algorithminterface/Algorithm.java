package com.pqsoft.pub.p2p.algorithm.algorithminterface;

import java.util.Map;

import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;


/**
 * 
 * 算法接口
 * 
 * @className Algorithm
 * @author 刘丽
 * @version V1.0 2016年3月30日 下午5:37:15
 */
public interface Algorithm<T extends CalculateItem> {

    /**
     * 获取算法的结果
     * 
     * @author 刘丽
     * @since 2016年3月30日17:36:34
     * @param model 表达式每项的值
     * @return Map<String, Object>
     * 
     *         合同总金额 htze (算法:合同总金额 = 租金 * 期数 + 尾款)
     *         列表 list (期数,租金,本金,利息,管理费,剩余本金)
     *         总期数 zqs (model.getD()从实体类中得到)
     *         总租金 zzj (算法:总租金=(月还 * 期数)+尾款)
     *         总本金zbj (算法:总本金=本金*(期数-1)+最后一期本金+尾款 )
     *         总利息zlx(算法:总利息=利息*(期数-1)+最后一期利息)
     */
    Map<String, Object> getCalculateResult(T model);

}
