package com.eks.other;

import com.eks.entity.PinDuoDuo;
import com.eks.utils.GsonUtils;
import com.eks.utils.SeleniumUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PinDuoDuoNormalTest {
    private WebDriver webDriver = SeleniumUtils.getWebDriver();
    @Before
    public void before() throws InterruptedException {
        String urlString = "https://jinbao.pinduoduo.com/promotion/single-promotion";
        String cookieFileNameString = SeleniumUtils.loginByCookie(webDriver, urlString);
        //登录名称
        Boolean loginBoolean = SeleniumUtils.checkLogin(webDriver, 30, "#__next > div.jsx-1070787851.container > section > div > div.jsx-322011365.login-wrapper.right > div > span.jsx-322011365.welcome-text");
        if (loginBoolean){
            SeleniumUtils.writeCookieToFile(webDriver,cookieFileNameString);
        }
        SeleniumUtils.executeScript(webDriver,"document.body.style.zoom = 0.7");
        Thread.sleep(2000);
        //知道了
        SeleniumUtils.clickByJs(webDriver,".animation.button");
        Thread.sleep(15000);
    }
    private static void setIntroductionAndUrl(WebDriver webDriver, WebElement btnWebElement, PinDuoDuo pinDuoDuo){
        try {
            //单击立即推广
            SeleniumUtils.clickByJs(webDriver, btnWebElement,2000);
            //单击确定
            SeleniumUtils.clickByJs(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper > div:nth-child(2) > div.pid-modal-wrapper > div > div.content > div.btn-wrapper > div:nth-child(1)",2000);
            //商品介绍
            String goodsIntroductionString  = SeleniumUtils.getContent(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper > div:nth-child(2) > div.share-info-wrapper > div > div > div.copy-wrapper > div > div > div > p:nth-child(1)");
            pinDuoDuo.setGoodsIntroduction(goodsIntroductionString);
            Thread.sleep(1000);
            //短链接
            String shortUrlString = SeleniumUtils.getContent(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper > div:nth-child(2) > div.share-info-wrapper > div > div > div.copy-wrapper > div > div > p.shortUrl");
            pinDuoDuo.setShortUrlString(shortUrlString);
            Thread.sleep(1000);
            //点击小程序标题
            SeleniumUtils.clickByJs(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper > div:nth-child(2) > div.share-info-wrapper > div > div > div.tab > ul:nth-child(1) > li:nth-child(5)",5000);
            //小程序图片链接
            String miniProgramImageUrlString = SeleniumUtils.getAttribute(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper > div:nth-child(2) > div.share-info-wrapper > div > div > div.copy-wrapper > div > img","src");
            pinDuoDuo.setMiniProgramImageUrl(miniProgramImageUrlString);
            Thread.sleep(1000);
            //点击取消
            SeleniumUtils.clickByJs(webDriver, "#__next > div.jsx-1070787851.container > main > div.single-promotion-wrapper > div:nth-child(2) > div.share-info-wrapper > div > div > div.btn-wrapper > div:nth-child(2)",10000);
            System.out.println(GsonUtils.convertObjectToJsonString(pinDuoDuo));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test1() {
        //a标签
        List<WebElement> aWebElementList = SeleniumUtils.findElements(webDriver, ".single-promotion-list > a");
        List<PinDuoDuo> pinDuoDuoList = new ArrayList<>();
        for(WebElement webElement : aWebElementList){
            PinDuoDuo pinDuoDuo = new PinDuoDuo();
            //商品ID
            String hrefString = webElement.getAttribute("href");
            String[] hrefStringArray = hrefString.split("=");
            String goodsIdString = hrefStringArray[hrefStringArray.length - 1];
            pinDuoDuo.setGoodsId(goodsIdString);
            //原价
            String goodsOriginalPriceString = SeleniumUtils.getContent(webElement,"a > div > div > div.goods-info > p.price-info > span.orign-price");
            pinDuoDuo.setGoodsOriginalPrice(goodsOriginalPriceString);
            //券后价
            String goodsDiscountPriceString = SeleniumUtils.getContent(webElement,"a > div > div > div.goods-info > p.price-info > span:nth-child(1) > b");
            pinDuoDuo.setGoodsDiscountPrice(goodsDiscountPriceString);
            //券后价
            String goodsEarnMoneyString = SeleniumUtils.getContent(webElement,"a > div > div > div.goods-info > p.earn-info > span:nth-child(1) > b");
            pinDuoDuo.setGoodsEarnMoney(goodsEarnMoneyString);
            //赚取的比例
            String goodsEarnRatioString = SeleniumUtils.getContent(webElement,"a > div > div > div.goods-info > p.earn-info > span:nth-child(2)");
            pinDuoDuo.setGoodsEarnRatio(goodsEarnRatioString);
            //设置商品介绍、短链接、小程序图片链接
            WebElement btnWebElement = SeleniumUtils.findElement(webElement, "a > div > div > div.goods-info > div.sale-info > div.btn");
            setIntroductionAndUrl(webDriver,btnWebElement,pinDuoDuo);
            //添加到List中
            pinDuoDuoList.add(pinDuoDuo);
        }
        System.out.println("pinDuoDuoList.size():" + pinDuoDuoList.size());
        System.out.println("pinDuoDuoList:" + GsonUtils.convertObjectToJsonString(pinDuoDuoList));
    }
}
