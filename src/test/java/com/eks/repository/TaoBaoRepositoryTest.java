package com.eks.repository;

import com.eks.Main;
import com.eks.entity.TaoBao;
import com.eks.utils.ClipboardUtils;
import com.eks.utils.GsonUtils;
import com.eks.utils.Pinyin4jUtils;
import com.eks.utils.RobotUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class TaoBaoRepositoryTest {
    @BeforeClass
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }
    @Autowired
    TaoBaoRepository taoBaoRepository;
    @Test
    public void test1() throws InterruptedException {
        TaoBao taoBao = new TaoBao();
        while (true) {
            Thread.sleep(5000);
            taoBao.setId(null);
            taoBao = taoBaoRepository.saveAndFlush(taoBao);
            System.out.println(GsonUtils.convertObjectToJsonString(taoBao));
        }
    }
    @Test
    public void test2() throws Exception {
        //创建输入流，接受目标excel文件
        FileInputStream fileInputStream = new FileInputStream("D:\\Study_Classification\\Generalize\\test.xls");
        //得到POI文件系统对象
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fileInputStream);
        //得到Excel工作簿对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
        //得到Excel工作簿的第一页，即excel工作表对象
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        //获取第一行即所有的标题
        HSSFRow firstHSSFRow = hssfSheet.getRow(0);
        int cellNumInt = firstHSSFRow.getPhysicalNumberOfCells();
        List<TaoBao> taoBaoList = new ArrayList<>();
        for(int i = 1,length = hssfSheet.getLastRowNum();i < length;i++){
            HSSFRow tempHSSFRow = hssfSheet.getRow(i);
            TaoBao taoBao = new TaoBao();
            taoBao.setIsDeleted(0);
            for(int j = 0;j < cellNumInt;j++){
                String titleString = firstHSSFRow.getCell(j).toString();
                String methodNameString = Pinyin4jUtils.getMethodNameFromHanzi(titleString, true);
                Method method = taoBao.getClass().getMethod(methodNameString, String.class);
                HSSFCell tempHSSFCell = tempHSSFRow.getCell(j);
                method.invoke(taoBao,tempHSSFCell.toString());
            }
            taoBaoList.add(taoBao);
        }
        taoBaoRepository.saveAll(taoBaoList);
    }
    @Test
    public void test3(){
        TaoBao taoBao = taoBaoRepository.findOneByIdAndIsDeleted(1, 0);
        ClipboardUtils.setClipboardText(GsonUtils.convertObjectToJsonString(taoBao));
        RobotUtils.clickMouse(500,500, 2553, 555);
        RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
    }
}
