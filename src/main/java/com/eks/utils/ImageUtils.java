package com.eks.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static Boolean judgeImage(String imageFilePathString){
        File file = new File(imageFilePathString);
        if (!file.exists()){
            throw new RuntimeException("File does not exist.");
        }
        try {
            Image image = ImageIO.read(file);
            if (image == null){
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        int widthInt = image.getWidth(null);
        int heightInt = image.getHeight(null);
        BufferedImage bufferedImage = null;
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
        bufferedImage = graphicsConfiguration.createCompatibleImage(widthInt, heightInt, Transparency.OPAQUE);
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(widthInt, heightInt, BufferedImage.TYPE_INT_RGB);
        }
        Graphics graphics = bufferedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return bufferedImage;
    }
    public static void write(RenderedImage renderedImage,String filePathString) throws IOException {
        String[] filePathStringArray = filePathString.split("\\.");
        ImageIO.write(renderedImage, filePathStringArray[filePathStringArray.length - 1], new File(filePathString));
    }
    public static void write(RenderedImage renderedImage,String formatNameString,String filePathString) throws IOException {
        ImageIO.write(renderedImage, formatNameString, new File(filePathString));
    }
}
