package com.pqsoft.pub.p2p.algorithm.model;

/**
 * 
 * 进位方式
 * 
 * @className CarryType
 * @author 刘丽
 * @version V1.0 2016年3月30日 下午2:23:37
 */
public enum IsRatioCalculate {
   yes(1), no(2) {
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

    private IsRatioCalculate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isRest() {
        return false;
    }
}
