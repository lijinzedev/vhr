package com.li.vhr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NavigableMap;
import java.util.TreeMap;

@SpringBootTest
class VhrApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(getGradeName(299));
    }

    public String getGradeName(Integer gradeValue) {
        NavigableMap<Integer, String> map = new TreeMap();
        map.put(100, "入门选手");
        map.put(200, "起步熟手");
        map.put(300, "坚韧黑铁");
        map.put(5000, "智者大师");
        map.put(50000, "只会传说");

        if (gradeValue < 0 || gradeValue > 50000) {
            return "默认的";
        } else {
            return map.floorEntry(gradeValue).getValue();
        }

    }
}
