package com.eks.normal;

import com.eks.utils.ClipboardUtils;
import com.eks.utils.FileUtils;
import com.eks.utils.GsonUtils;
import com.eks.utils.RobotUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.Random;

public class PinDuoDuoNormalTest {
    @Test
    public void test1() throws Exception {
        String filePathString = FileUtils.generatePathBaseProjectPath("extra/json/20190811_2158_PinDuoDuo_Theme.json");
        String jsonString = FileUtils.convertFileToContentString(filePathString);
        JsonArray jsonArray = GsonUtils.convertJsonStringToJsonElement(jsonString).getAsJsonArray();
        int sizeInt = jsonArray.size();
        int anInt = 0;
        JsonObject jsonObject = null;
        Random random = new Random();
        StringBuilder stringBuilder = null;
        while (true) {
            try {
                anInt = random.nextInt(sizeInt);
                jsonObject = jsonArray.get(anInt).getAsJsonObject();
                stringBuilder = new StringBuilder();
                stringBuilder.append("@所有人 各位亲打扰一下下[玫瑰]");
                stringBuilder.append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("【拼多多主题推广】");
                stringBuilder.append(jsonObject.get("theme").getAsString());
                stringBuilder.append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("买不买没关系,先领券,先领券!!![爱心][爱心][爱心]");
                stringBuilder.append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("【链接】");
                stringBuilder.append(jsonObject.get("url").getAsString());
                stringBuilder.append("\n");
                String contentString = stringBuilder.toString();
                System.out.println(contentString);
                Thread.sleep(2000);
                ClipboardUtils.setClipboardText(contentString);
                Thread.sleep(2000);
                //文本框
                RobotUtils.clickMouse(500,500, 1456, 486);
                RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_A);
                RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
                RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_A);
                RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_C);
                Thread.sleep(2000);
                //用户头像
                RobotUtils.clickMouse(500,500, 2028, 256);
                Thread.sleep(2000);
                //输入框
                RobotUtils.clickMouse(500,500, 2584, 544);
                Thread.sleep(2000);
                RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
                RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
                Thread.sleep(2000);
                //关闭当前会话
//                RobotUtils.clickMouse(1000,1000, 2137, 255);
                Thread.sleep(1000 * 60 * 3);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
