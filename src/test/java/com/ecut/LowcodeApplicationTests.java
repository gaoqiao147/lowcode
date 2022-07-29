package com.ecut;

import com.freesoft.mapper.ApiMainMapper;
import com.freesoft.model.ApiMainDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class LowcodeApplicationTests {
    @Test
    void contextLoads() {
    }

    @Test
    public void test(){

    }
    @Resource
    ApiMainMapper apiMainMapper;
    @Test
    public List<ApiMainDO> getAllApi() {
        List<ApiMainDO> list = apiMainMapper.selectList(null);
        System.out.println(list);
        return list;
    }
}
