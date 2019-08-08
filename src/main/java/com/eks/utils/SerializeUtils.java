package com.eks.utils;

import java.io.*;

public class SerializeUtils {
    public static void writeObject(String filePathString,Object object){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(filePathString);
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AutoCloseableUtils.close(objectOutputStream);
            AutoCloseableUtils.close(fileOutputStream);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> T readObject(String filePathString){
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        try {
            File file = new File(filePathString);
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (T)objectInputStream.readObject();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            AutoCloseableUtils.close(objectInputStream);
        }
        return null;
    }
}
