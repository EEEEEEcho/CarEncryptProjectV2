package com.echo.service;

import java.util.List;
import java.util.Map;

public interface KGCMasterService {
    public void updateKgc(Map<String, Object> map);

    public void updateKeyPair(Map<String,Object> map);

    public List<Map<String,String>> getKeyPair();
}
