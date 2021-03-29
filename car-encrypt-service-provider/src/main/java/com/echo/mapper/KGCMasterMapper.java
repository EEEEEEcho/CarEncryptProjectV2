package com.echo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface KGCMasterMapper {
    public void updateKgc(Map<String, Object> map);

    public void updateKeyPair(Map<String,Object> map);

    public List<Map<String,String>> getKeyPair();

    public String getPublicEncryptMasterKey();

    public String getPrivateEncryptMasterKey();

    public String getPublicSignMasterKey();

    public String getPrivateSignMasterKey();
}
