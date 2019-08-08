package com.eks.thread;

import com.eks.entity.PinDuoDuo;
import com.eks.utils.GsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PinDuoDuoThreadTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        Integer totalInteger = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(totalInteger);
        List<Future<List<PinDuoDuo>>> futureList = new ArrayList<>();
        for(int i = 0;i < totalInteger;i++){
            PinDuoDuoThread pinDuoDuoThread = new PinDuoDuoThread(i,totalInteger);
            Future<List<PinDuoDuo>> future = executorService.submit(pinDuoDuoThread);
            futureList.add(future);
        }
        List<PinDuoDuo> pinDuoDuoList = new ArrayList<>();
        for(Future<List<PinDuoDuo>> future : futureList){
            List<PinDuoDuo> tempPinDuoDuoList = future.get();
            pinDuoDuoList.addAll(tempPinDuoDuoList);
        }
        for(PinDuoDuo pinDuoDuo : pinDuoDuoList){
            System.out.println(GsonUtils.convertObjectToJsonString(pinDuoDuo));
        }
    }
}
