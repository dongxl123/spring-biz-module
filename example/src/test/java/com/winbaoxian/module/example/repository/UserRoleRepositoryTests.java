package com.winbaoxian.module.example.repository;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.example.BaseTest;
import com.winbaoxian.module.security.repository.UserRoleRepository;
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
