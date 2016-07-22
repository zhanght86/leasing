package com.pqsoft.pub.p2p.algorithm.model;

/**
 * 
 * 租金保留位数
 * 
 * @className DigitType
 * @author 刘丽
 * @version V1.0 2016年3月23日 下午1:05:57
 */
public enum DigitType {

    两位小数(2), 取整(4) {
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

    private DigitType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isRest() {
        return false;
    }
}
