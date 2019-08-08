package com.eks.thread;

import com.eks.entity.PinDuoDuo;
import com.eks.utils.GsonUtils;
import com.eks.utils.SeleniumUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor//全参构造器
public class PinDuoDuoThread implements Callable<List<PinDuoDuo>>{
    private Integer indexInteger;
    private Integer totalInteger;
    @Override
    public List<PinDuoDuo> call() throws Exception {
        WebDriver webDriver = SeleniumUtils.getWebDriver();
        SeleniumUtils.loginByCookie(webDriver, "https://jinbao.pinduoduo.com/promotion/single-promotion");
        //登录名称
        Boolean loginBoolean = SeleniumUtils.checkLogin(webDriver, 10, "#__next > div.jsx-1070787851.container > section > div > div.jsx-322011365.login-wrapper.right > div > span.jsx-322011365.welcome-text");
        if (!loginBoolean){
            throw new RuntimeException("Fail to login.");
        }
        Thread.sleep(1000);
        //知道了
        SeleniumUtils.click(webDriver,".animation.button");

        List<WebElement> webElementList = SeleniumUtils.findElements(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.single-promotion-list > a");
        List<PinDuoDuo> pinDuoDuoList = new ArrayList<>();
        for(int i = 1,length = webElementList.size();i <= length;i++){
            if ((i % totalInteger) == indexInteger){
                try {
                    WebElement webElement = webElementList.get(i);

                    PinDuoDuo pinDuoDuo = new PinDuoDuo();

                    String hrefString = webElement.getAttribute("href");
                    String[] hrefStringArray = hrefString.split("=");
                    String goodsIdString = hrefStringArray[hrefStringArray.length - 1];
                    pinDuoDuo.setGoodsIdString(goodsIdString);

                    //立即推广
                    WebElement btnWebElement = SeleniumUtils.findElement(webElement, "div > div > div.goods-info > div.sale-info > div.btn");
                    SeleniumUtils.click(webDriver, btnWebElement);
                    //确定
                    WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
                    webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.pid-modal-wrapper > div > div.content > div.btn-wrapper > div:nth-child(1)")));
                    SeleniumUtils.click(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.pid-modal-wrapper > div > div.content > div.btn-wrapper > div:nth-child(1)");
                    Thread.sleep(1000);
                    //长链接
                    String weChatLongUrlString = SeleniumUtils.getAttribute(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.share-info-wrapper > div > div > div.copy-wrapper > div > div > p.weChatLongUrl", "innerHTML");
                    pinDuoDuo.setWeChatLongUrlString(weChatLongUrlString);
                    //小程序
                    SeleniumUtils.click(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.share-info-wrapper > div > div > div.tab > ul:nth-child(1) > li:nth-child(5)");
                    Thread.sleep(3000);
                    webDriverWait = new WebDriverWait(webDriver, 5);
                    webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.share-info-wrapper > div > div > div.copy-wrapper > div > img")));
                    String miniProgramImageUrlString = SeleniumUtils.getAttribute(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.share-info-wrapper > div > div > div.copy-wrapper > div > img", "src");
                    pinDuoDuo.setMiniProgramImageUrlString(miniProgramImageUrlString);
                    //取消
                    SeleniumUtils.click(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper.main-content > div.share-info-wrapper > div > div > div.btn-wrapper > div:nth-child(2)");
                    Thread.sleep(1000);
                    pinDuoDuoList.add(pinDuoDuo);
                    System.out.println("ThreadId:" + indexInteger + ",GoodsIndex:" + i + ",PinDuoDuo:" + GsonUtils.convertObjectToJsonString(pinDuoDuo));
                }catch (Exception e){
                    System.out.println("ERROR:ThreadId:" + indexInteger + ",GoodsIndex:" + i + ",PinDuoDuo:");
                    WebDriver.Navigation navigation = webDriver.navigate();
                    navigation.refresh();
                    Thread.sleep(5000);
                }
            }
        }
        return pinDuoDuoList;
    }
}
