package com.mycompany.myapp.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-liyuan.j
 * @date 2016-07-01
 */
public class HeepOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
