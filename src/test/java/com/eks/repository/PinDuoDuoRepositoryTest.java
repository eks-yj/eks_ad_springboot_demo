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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
        String filePathString = FileUtils.generatePathBaseProjectPath("extra/json/20190810_1441_PinDuoDuo");
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
    public void sendAd(List<WeChatPoint> weChatPointList, int startId){
        int i = startId;
        while (true){
            try {
                PinDuoDuo pinDuoDuo = pinDuoDuoRepository.findOneByIdAndIsDeleted(i, 0);
                if (i < 60){
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
                StringBuilder stringBuilder = new StringBuilder("[玫瑰][玫瑰][玫瑰]【加我为好友进特惠群】");
                boolean samePriceBoolean = false;
                if (goodsOriginalPriceDouble.compareTo(goodsDiscountPriceDouble) == 0){
                    samePriceBoolean = true;
                    stringBuilder.append("活动特价:");
                    stringBuilder.append(goodsOriginalPriceDouble);
                }else{
                    stringBuilder.append("劲省");
                    stringBuilder.append(goodsOriginalPriceDouble - goodsDiscountPriceDouble);
                }
                stringBuilder.append("元");
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
    public static void sendAdToWeChat(Integer xInteger,Integer yInteger,String contentString,Image image) throws InterruptedException, IOException {
        Thread.sleep(2000);
        ClipboardUtils.setClipboardText(contentString);
        RobotUtils.clickMouse(500,500, xInteger, yInteger);
        RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
        Thread.sleep(2000);
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
        this.sendAd(weChatPointList,40);
    }
}
