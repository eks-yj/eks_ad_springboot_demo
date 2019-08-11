package com.eks.utils;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class RobotUtilsTest {
    @Test
    public void test1() {
        Point point = RobotUtils.getMousePoint();
        System.out.println("X: " + Double.valueOf(point.getX()).intValue());
        System.out.println("Y: " + Double.valueOf(point.getY()).intValue());
    }
    @Test
    public void test2() throws IOException {
        String miniProgramImageUrlString = "http://images.pinduoduo.com/mrk/2019-08-11/eb3c9b48-bbda-47bb-94e5-ad4f2c24c74a.jpg";
        BufferedImage bufferedImage = ImageIO.read(new URL(miniProgramImageUrlString));
        ImageUtils.write(bufferedImage,"d:/apple.jpg");
        ClipboardUtils.setClipboardImage(bufferedImage);
    }
}
