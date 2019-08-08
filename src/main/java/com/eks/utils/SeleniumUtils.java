package com.eks.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SeleniumUtils {
    private static String basalCookieSetPathString = null;
    static {
        String filePathString = FileUtils.generatePathBaseProjectPath("extra/driver/chromedriver_win32/chromedriver.exe");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,filePathString);
        basalCookieSetPathString = FileUtils.generatePathBaseProjectPath("extra/cookie/");
    }
    public static WebDriver getWebDriver(){
        List<String> argumentStringList = new ArrayList<>();
        //设置为 headless 模式
//        argumentStringList.add("--headless");
        //以中文utf-8编码格式启动浏览器
        argumentStringList.add("--lang=zh_CN.UTF-8");
        //设置浏览器最大化,其实此处无必要，直接在代码中设置最大化即可，不建议使用
        argumentStringList.add("--start-maximized");
        return getWebDriver(argumentStringList.toArray(new String[argumentStringList.size()]));
    }
    public static WebDriver getWebDriver(String... argumentStringArray){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(argumentStringArray);
        return new ChromeDriver(chromeOptions);
    }
    public static String loginByCookie(WebDriver webDriver, String urlString){
        WebDriver.Options options = webDriver.manage();
        WebDriver.Window window = options.window();
        window.maximize();
        webDriver.get(urlString);
        String[] urlSplitStringArray = urlString.split("\\.");
        String cookieFileNameString = urlSplitStringArray[1];
        Set<Cookie> cookieSet = SerializeUtils.readObject(basalCookieSetPathString + cookieFileNameString);
        if (cookieSet != null && cookieSet.size() > 0){
            for(Cookie cookie : cookieSet){
                options.addCookie(cookie);
            }
        }
        WebDriver.Navigation navigation = webDriver.navigate();
        navigation.refresh();
        return cookieFileNameString;
    }
    public static Boolean checkLogin(WebDriver webDriver,long timeOutInSeconds,String cssSelectorString) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeOutInSeconds);
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelectorString)));
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static void writeCookieToFile(WebDriver webDriver, String cookieFileName){
        WebDriver.Options options = webDriver.manage();
        Set<Cookie> cookieSet = options.getCookies();
        if (cookieSet == null || cookieSet.size() == 0){
            throw new RuntimeException("Could not get cookie from browser.");
        }
        SerializeUtils.writeObject(basalCookieSetPathString + cookieFileName,cookieSet);
    }
    public static void sendKeys(WebDriver webDriver,String cssSelectorString,String contentString){
        WebElement webElement = findElement(webDriver,cssSelectorString);
        webElement.sendKeys(contentString);
    }
    public static void click(WebDriver webDriver,WebElement webElement){
        click(webDriver,webElement,null,null);
    }
    public static void click(WebDriver webDriver,String cssSelectorString){
        click(webDriver,cssSelectorString,null,null);
    }
    public static void clickOnBody(WebDriver webDriver,Integer xOffsetInteger, Integer yOffsetInteger){
        click(webDriver,"body",xOffsetInteger,yOffsetInteger);
    }
    public static void click(WebDriver webDriver,String cssSelectorString,Integer xOffsetInteger, Integer yOffsetInteger){
        WebElement webElement = findElement(webDriver,cssSelectorString);
        click(webDriver,webElement,xOffsetInteger,yOffsetInteger);
    }
    public static void click(WebDriver webDriver,WebElement webElement,Integer xOffsetInteger, Integer yOffsetInteger){
        Actions actions = new Actions(webDriver);
        if (xOffsetInteger != null && yOffsetInteger != null){
            actions.moveToElement(webElement,xOffsetInteger,yOffsetInteger);
        }else {
            actions.moveToElement(webElement);
        }
        actions.click();
        actions.release();
        actions.perform();
    }
    public static Object executeScript(WebDriver webDriver,String scriptString){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;
        return javascriptExecutor.executeScript(scriptString);
    }
    public static WebElement findElement(SearchContext searchContext,String cssSelectorString){
        return searchContext.findElement(By.cssSelector(cssSelectorString));
    }
    public static List<WebElement> findElements(SearchContext searchContext,String cssSelectorString){
        return searchContext.findElements(By.cssSelector(cssSelectorString));
    }
    public static String getAttribute(SearchContext searchContext,String cssSelectorString,String attributeString){
        WebElement webElement = findElement(searchContext,cssSelectorString);
        return webElement.getAttribute(attributeString);
    }
}
