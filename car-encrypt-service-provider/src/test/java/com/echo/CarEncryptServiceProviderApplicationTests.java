package com.echo;

import com.echo.mapper.TmpExchangeInfoMapper;
import com.echo.service.KGCMasterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class CarEncryptServiceProviderApplicationTests {
//    @Autowired
//    KGCMasterService kgcMasterService;
    @Autowired
    TmpExchangeInfoMapper tmpExchangeInfoMapper;
    @Test
    void contextLoads() {
        String car = tmpExchangeInfoMapper.findServerTmpKey("ADERXWRY78VC78W");
        System.out.println(car);
//        List<Map<String, String>> keyPair = kgcMasterService.getKeyPair();
//        System.out.println(keyPair);
    }
}
