package com.winbaoxian.module.example;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * @Author DongXL
 * @Create 2018-05-09 17:13
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseTest {
    public static final int MAXIMUM_CAPACITY = 2147483647;


}
