package com.eks.repository;

import com.eks.Main;
import com.eks.entity.PinDuoDuo;
import com.eks.utils.GsonUtils;
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
        pinDuoDuo.setGoodsIdString("goodsId");
        pinDuoDuo.setMiniProgramImageUrlString("mini_url");
        while (true) {
            Thread.sleep(5000);
            pinDuoDuo.setId(null);
            pinDuoDuo = pinDuoDuoRepository.saveAndFlush(pinDuoDuo);
            System.out.println(GsonUtils.convertObjectToJsonString(pinDuoDuo));
        }
    }
}
