package com.pqsoft.pub.p2p.algorithm.model;

import java.math.BigDecimal;

/**
 * 
 * 计算项
 * 
 * @className CalculateItem
 * @author 刘丽
 * @version V1.0 2016年3月23日 上午10:30:41
 */
public class CalculateItem {

    private BigDecimal a;// 融资额

    private BigDecimal b;// 尾款

    private BigDecimal c;// 年利率

    private Integer d;// 期数

    private BigDecimal e;// 管理费

    private BigDecimal f;// 保证金

    private DigitType digitType;// 保留几位小数

    private AlgorithmType algorithmType;// 算法类型

    private CarryType carryType;// 进位方式

    private BigDecimal monthComprehensiveRate;// 月综合费率

    private BigDecimal nblv;// 内部利率

    private IsRatioCalculate isRatioCalculate;//是否按照比例计算 
    
    private BigDecimal additionalCosts;//额外费用
    
    private BigDecimal monthAdministration;//每期管理费  自己上牌费
    
    private BigDecimal clientAdministration;//客户上牌费
    
    /**
     * 得到客户管理费  客户上牌费
     * @return
     */
    public BigDecimal getClientAdministration() {
		return clientAdministration;
	}

    /**
     * 设置 客户管理费 
     * @param clientAdministration
     */
	public void setClientAdministration(BigDecimal clientAdministration) {
		this.clientAdministration = clientAdministration;
	}

	/**
     * 得到每期管理费
     * @return
     */
    public BigDecimal getMonthAdministration() {
		return monthAdministration;
	}

    /**
     * 设置每期管理费
     * @param monthAdministration
     */
	public void setMonthAdministration(BigDecimal monthAdministration) {
		this.monthAdministration = monthAdministration;
	}

	/**
     * 得到额外费用
     * @return
     */
    public BigDecimal getAdditionalCosts() {
		return additionalCosts;
	}

    /**
     * 设置额外费用
     * @param additionalCosts
     */
	public void setAdditionalCosts(BigDecimal additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	/**
     * 得到比例计算
     * @return
     */
    public IsRatioCalculate getIsRatioCalculate() {
		return isRatioCalculate;
	}

    /**
     * 设置比例计算 如果有值并且是yes,那么按照比例计算,否则一律不计算[即不加额外费用]
     * @param isRatioCalculate
    */
	public void setIsRatioCalculate(IsRatioCalculate isRatioCalculate) {
		this.isRatioCalculate = isRatioCalculate;
	}

	/**
     * 得到内部利率
     * 
     * @return
     */
    public BigDecimal getNblv() {
        return nblv;
    }

    /**
     * 设置内部利率
     * 
     * @param nblv
     */
    public void setNblv(BigDecimal nblv) {
        this.nblv = nblv;
    }

    /**
     * 得到月综合费率
     * 
     * @return
     */
    public BigDecimal getMonthComprehensiveRate() {
        return monthComprehensiveRate;
    }

    /**
     * 设置月综合费率
     * 
     * @param monthComprehensiveRate
     */
    public void setMonthComprehensiveRate(BigDecimal monthComprehensiveRate) {
        this.monthComprehensiveRate = monthComprehensiveRate;
    }

    /**
     * 得到尾款
     * 
     * @return
     */
    public BigDecimal getB() {
        return b;
    }

    /**
     * 设置尾款
     * 
     * @param b
     */
    public void setB(BigDecimal b) {
        this.b = b;
    }

    /**
     * 得到租金保留位数
     * 
     * @return DigitType 枚举类型
     */
    public DigitType getDigitType() {
        return digitType;
    }

    /**
     * 设置租金的保留位数
     * 
     * @param digitType 枚举类型
     */
    public void setDigitType(DigitType digitType) {
        this.digitType = digitType;
    }

    /**
     * 得到融资额
     * 
     * @return BigDecimal
     */
    public BigDecimal getA() {
        return a;
    }

    /**
     * 设置融资额
     * 
     * @param a
     */
    public void setA(BigDecimal a) {
        this.a = a;
    }

    /**
     * 得到年利率
     * 
     * @return BigDecimal
     */
    public BigDecimal getC() {
        return c;
    }

    /**
     * 设置年利率
     * 
     * @param c
     */
    public void setC(BigDecimal c) {
        this.c = c;
    }

    /**
     * 得到期数
     * 
     * @return Integer
     */
    public Integer getD() {
        return d;
    }

    /**
     * 设置期数
     * 
     * @param d
     */
    public void setD(Integer d) {
        this.d = d;
    }

    /**
     * 得到管理费
     * 
     * @return
     */
    public BigDecimal getE() {
        return e;
    }

    /**
     * 设置管理费
     * 
     * @param e
     */
    public void setE(BigDecimal e) {
        this.e = e;
    }

    /**
     * 得到保证金
     *
     * @return
     */
    public BigDecimal getF() {
        return f;
    }

    /**
     * 设置保证金
     *
     * @param f
     */
    public void setF(BigDecimal f) {
        this.f = f;
    }

    /**
     * 得到算法类型
     * 
     * @return AlgorithmType
     */
    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    /**
     * 设置算法类型
     * 
     * @param algorithmType 枚举
     */
    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    /**
     * 得到进位方式
     * 
     * @return CarryType
     */
    public CarryType getCarryType() {
        return carryType;
    }

    /**
     * 设置进位方式
     * 
     * @param carryType 枚举
     */
    public void setCarryType(CarryType carryType) {
        this.carryType = carryType;
    }

}
