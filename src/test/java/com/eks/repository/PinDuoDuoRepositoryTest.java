package com.eks.repository;

import com.eks.Main;
import com.eks.entity.PinDuoDuo;
import com.eks.utils.FileUtils;
import com.eks.utils.GsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
