package com.echo.encrypt;


import com.echo.encrypt.test.SM9Test;

public class Main {

    public static void main(String[] args)
    {
//        SM9Test.test(0, false); //SM9测试，随机生成密钥

        SM9Test.test(1, false); //SM9测试，验证《GMT 0044-2016 SM9标识密码算法：第5部分 参数定义》中的测试数据

//        SM9Test.test(1, true); //SM9测试，对SM9中涉及到的数据结构进行重构测试
    }


    public static void showMsg(String msg) {
        System.out.println(msg);
    }

}
