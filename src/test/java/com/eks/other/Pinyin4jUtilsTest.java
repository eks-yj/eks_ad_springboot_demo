package com.eks.other;

import com.eks.utils.Pinyin4jUtils;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;

public class Pinyin4jUtilsTest {
    @Test
    public void test1() throws BadHanyuPinyinOutputFormatCombination {
        String methodNameString = Pinyin4jUtils.getMethodNameFromHanzi("测试id", true);
        System.out.println(methodNameString);
    }
}
