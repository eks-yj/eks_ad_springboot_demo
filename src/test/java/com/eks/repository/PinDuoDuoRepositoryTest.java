package com.eks.repository;

import com.eks.Main;
import com.eks.entity.PinDuoDuo;
import com.eks.pojo.WeChatPoint;
import com.eks.utils.ClipboardUtils;
import com.eks.utils.FileUtils;
import com.eks.utils.GsonUtils;
import com.eks.utils.RobotUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class PinDuoDuoRepositoryTest {
    @Autowired
    PinDuoDuoRepository pinDuoDuoRepository;
    @Test
    public void test1() throws InterruptedException {
        PinDuoDuo pinDuoDuo = new PinDuoDuo();
        pinDuoDuo.setGoodsId("goodsId");
        pinDuoDuo.setMiniProgramImageUrl("mini_url");
        while (true) {
            Thread.sleep(5000);
            pinDuoDuo.setId(null);
            pinDuoDuo = pinDuoDuoRepository.saveAndFlush(pinDuoDuo);
            System.out.println(GsonUtils.convertObjectToJsonString(pinDuoDuo));
        }
    }
    @Test
    public void test2() throws Exception {
        String filePathString = FileUtils.generatePathBaseProjectPath("extra/json/20190811_1826_PinDuoDuo_辣条_233_292");
        String contentString = FileUtils.convertFileToContentString(filePathString);
        JsonArray jsonArray = GsonUtils.convertJsonStringToJsonElement(contentString).getAsJsonArray();
        for(JsonElement jsonElement : jsonArray){
            try {
                PinDuoDuo pinDuoDuo = GsonUtils.convertJsonStringToT(jsonElement.toString(), PinDuoDuo.class);
                pinDuoDuo.setIsDeleted(0);
                pinDuoDuo = pinDuoDuoRepository.saveAndFlush(pinDuoDuo);
                System.out.println(GsonUtils.convertObjectToJsonString(pinDuoDuo));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void sendAd(List<WeChatPoint> weChatPointList, int startId,int endId){
        int i = startId;
        while (true){
            try {
                PinDuoDuo pinDuoDuo = pinDuoDuoRepository.findOneByIdAndIsDeleted(i, 0);
                if (i < endId){
                    i = i + 1;
                }else{
                    i = 1;
                }
                String goodsIntroductionString = pinDuoDuo.getGoodsIntroduction();

                String goodsOriginalPriceString = pinDuoDuo.getGoodsOriginalPrice();
                Double goodsOriginalPriceDouble = getPrice(goodsOriginalPriceString);

                String goodsDiscountPriceString = pinDuoDuo.getGoodsDiscountPrice();
                Double goodsDiscountPriceDouble = getPrice(goodsDiscountPriceString);

                String shortUrlString = pinDuoDuo.getShortUrlString();
                String miniProgramImageUrlString = pinDuoDuo.getMiniProgramImageUrl();
                Random random = new Random();
                int randomInt = random.nextInt(5);
                StringBuilder stringBuilder = new StringBuilder();
                if (233 <= pinDuoDuo.getId() && pinDuoDuo.getId() <= 292){
                    stringBuilder.append("[玫瑰][玫瑰][玫瑰]");
                    stringBuilder.append("大品牌辣条推荐,超好吃");
                    stringBuilder.append("\n");
                }
                if (randomInt == 0){
                    stringBuilder.append("[玫瑰][玫瑰][玫瑰]");
                }else if (randomInt == 1){
                    stringBuilder.append("[红包][红包][红包]");
                }else if (randomInt == 2){
                    stringBuilder.append("[爱心][爱心][爱心]");
                }else {
                    stringBuilder.append("恭喜发财!");
                }
                stringBuilder.append("【");
                if (randomInt == 0){
                    stringBuilder.append("促销最后倒计时");
                }else if (randomInt == 1){
                    stringBuilder.append("特惠商品手慢则无");
                }else if (randomInt == 2){
                    stringBuilder.append("官方品牌大促销");
                }else{
                    stringBuilder.append("爆款产品限时特惠");
                }
                stringBuilder.append("】");
                stringBuilder.append("\n");
                stringBuilder.append("【");
                boolean samePriceBoolean = false;
                if (goodsOriginalPriceDouble.compareTo(goodsDiscountPriceDouble) == 0){
                    samePriceBoolean = true;
                    stringBuilder.append("【活动特价:");
                    stringBuilder.append(goodsOriginalPriceDouble);
                }else{
                    stringBuilder.append("劲省");
                    Double moneyDouble = goodsOriginalPriceDouble - goodsDiscountPriceDouble;
                    int moneyInt = moneyDouble.intValue();
                    if(moneyDouble > moneyInt){
                        stringBuilder.append(goodsOriginalPriceDouble - goodsDiscountPriceDouble);
                    }else {
                        stringBuilder.append(moneyInt);
                    }
                }
                stringBuilder.append("元】");
                stringBuilder.append("\n");
                stringBuilder.append(goodsIntroductionString);
                stringBuilder.append("\n");
                if (samePriceBoolean){
                    stringBuilder.append("【特价】");
                    stringBuilder.append(goodsOriginalPriceDouble);
                }else {
                    stringBuilder.append("【原价】");
                    stringBuilder.append(goodsOriginalPriceDouble);
                    stringBuilder.append("\n");
                    stringBuilder.append("【内部专享价】");
                    stringBuilder.append(goodsDiscountPriceDouble);
                }
                stringBuilder.append("\n");
                stringBuilder.append("\n");

                stringBuilder.append("【购买方式一】点击以下链接");
                stringBuilder.append("\n");
                stringBuilder.append(getUrl(shortUrlString));
                stringBuilder.append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("【购买方式二】点击下图并长按前往小程序");
                String contentString = stringBuilder.toString();
                Image image = ImageIO.read(new URL(miniProgramImageUrlString));
                if (image == null){
                    Thread.sleep(2000);
                    image = ImageIO.read(new URL(miniProgramImageUrlString));
                    Thread.sleep(2000);
                }
                for(WeChatPoint weChatPoint : weChatPointList){
                    try {
                        sendAdToWeChat(weChatPoint.getXInteger(), weChatPoint.getYInteger(), contentString, image);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println("当前时间:" + new Date());
                Thread.sleep(1000 * 60 * 3);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static Double getPrice(String priceString){
        String[] priceStringArray = priceString.split("￥");
        priceString = priceStringArray[priceStringArray.length - 1];
        return Double.valueOf(priceString);
    }
    public static String getUrl(String string){
        return string.substring("商品链接:".length(),string.length());
    }
    public static void sendAdToWeChat(Integer xInteger,Integer yInteger,String contentString,Image image) throws InterruptedException {
        Thread.sleep(2000);
        ClipboardUtils.setClipboardText(contentString);
        RobotUtils.clickMouse(500,500, xInteger, yInteger);
        RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
        Thread.sleep(3000);
        ClipboardUtils.setClipboardImage(image);
        RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        RobotUtils.pressKey(500, false, KeyEvent.VK_ENTER);
        Thread.sleep(2000);
    }
    @Test
    public void test3(){
        List<WeChatPoint> weChatPointList = new ArrayList<>();
        weChatPointList.add(new WeChatPoint(2107,249));
        weChatPointList.add(new WeChatPoint(2529,249));
        weChatPointList.add(new WeChatPoint(2941,249));
        weChatPointList.add(new WeChatPoint(3233,249));

        weChatPointList.add(new WeChatPoint(2107,625));
        weChatPointList.add(new WeChatPoint(2529,625));
        weChatPointList.add(new WeChatPoint(2941,625));
        weChatPointList.add(new WeChatPoint(3233,625));
        this.sendAd(weChatPointList,233,292);
    }
}
