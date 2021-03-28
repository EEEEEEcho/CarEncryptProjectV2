package com.echo.listener;

import com.echo.encrypt.KGCMaster;
import com.echo.encrypt.ServerMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitListener implements ApplicationRunner {
    @Autowired
    KGCMaster kgcMaster;
    @Autowired
    ServerMaster serverMaster;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始执行！@！！！！！！！！！！");
        kgcMaster.init();
        serverMaster.init();
    }
}
