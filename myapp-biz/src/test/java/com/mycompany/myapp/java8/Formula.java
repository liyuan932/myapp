package com.mycompany.myapp.java8;

/**
 * @author wb-liyuan.j
 * @date 2016-12-09
 */
public interface Formula {
    double calculate(int a);
    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
