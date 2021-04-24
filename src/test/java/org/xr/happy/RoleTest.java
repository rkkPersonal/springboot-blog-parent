package org.xr.happy;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)

public class RoleTest {

    @Mock
    @InjectMocks
    UserMapper dao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sayHello() {

        // any()代替任意类型的参数
        Mockito.doReturn(new User()).when(dao).selectOne(any());
        // 没有返回值的方法，可以不另外写，因为模拟实体类的时候已经自动模拟了
        Mockito.doNothing().when(dao).selectAll();

        Assert.isTrue(true, "完全正确的单元测试");
    }


    public static void main(String[] args) {


        int i =1;

        i<<=10;

        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));

    }

    /**
     * Before >>> , i3's value is -4992
     * i3's binary string is 11111111111111111110110010000000
     * After >>> , i3's value is 4194299
     * i3's binary string is 1111111111111111111011
     */
    private static void test3() {
        int i3 = -4992;
        System.out.println("Before >>> , i3's value is " + i3);
        System.out.println("i3's binary string is " + Integer.toBinaryString(i3));
        i3 >>>= 10;
        System.out.println("After >>> , i3's value is " + i3);
        System.out.println("i3's binary string is " + Integer.toBinaryString(i3));
    }

    /**
     * Before >> , i1's value is 4992
     * i1's binary string is 1001110000000
     * After >> , i1's value is 4
     * i1's binary string is 100
     * Before >> , i2's value is -4992
     * i2's binary string is 11111111111111111110110010000000
     * After >> , i2's value is -5
     * i2's binary string is 11111111111111111111111111111011
     */
    private static void test2() {
        // 对正数进行右移操作
        int i1 = 4992;
        System.out.println("Before >> , i1's value is " + i1);
        System.out.println("i1's binary string is " + Integer.toBinaryString(i1));
        i1 >>= 10;
        System.out.println("After >> , i1's value is " + i1);
        System.out.println("i1's binary string is " + Integer.toBinaryString(i1));
        // 对负数进行右移操作
        int i2 = -4992;
        System.out.println("Before >> , i2's value is " + i2);
        System.out.println("i2's binary string is " + Integer.toBinaryString(i2));
        i2 >>= 10;
        System.out.println("After >> , i2's value is " + i2);
        System.out.println("i2's binary string is " + Integer.toBinaryString(i2));
    }

    /**
     * Before << , i's value is -1
     * i's binary string is 11111111111111111111111111111111
     * After << , i's value is -1024
     * i's binary string is 11111111111111111111110000000000
     */
    private static void test1() {
        int i = -1;
        System.out.println("Before << , i's value is " + i);
        System.out.println("i's binary string is " + Integer.toBinaryString(i));
        i <<= 10;
        System.out.println("After << , i's value is " + i);
        System.out.println("i's binary string is " + Integer.toBinaryString(i));
    }
}
