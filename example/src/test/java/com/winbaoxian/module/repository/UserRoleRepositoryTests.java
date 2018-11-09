package com.winbaoxian.module.repository;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class UserRoleRepositoryTests extends BaseTest {

    @Resource
    private UserRoleRepository userRoleRepository;

    @Test
    public void testFindUserRoleIdByUserId() {
        List list = userRoleRepository.findByUserId(16L);
        System.out.println(JSON.toJSONString(list));
    }

}
