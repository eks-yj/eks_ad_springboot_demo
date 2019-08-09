package com.eks.utils;

import org.junit.Test;

import java.awt.*;

public class RobotUtilsTest {
    @Test
    public void test1() {
        Point point = RobotUtils.getMousePoint();
        System.out.println("X: " + Double.valueOf(point.getX()).intValue());
        System.out.println("Y: " + Double.valueOf(point.getY()).intValue());
    }
}
