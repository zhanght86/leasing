package com.pqsoft.pub.p2p.algorithm.model;

/**
 * 
 * 算法类型
 * 
 * @className AlgorithmType
 * @author 刘丽
 * @version V1.0 2016年3月30日 下午1:46:13
 */
public enum AlgorithmType {

    平息法(7), 期初等额本金(4), 期末等额本金(1), 期初不等额(2), 期末不等额(3) {
        @Override
        public boolean isRest() {
            return true;
        }
    },
    SUN(0) {
        @Override
        public boolean isRest() {
            return true;
        }
    };

    private int value;

    private AlgorithmType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isRest() {
        return false;
    }
}
