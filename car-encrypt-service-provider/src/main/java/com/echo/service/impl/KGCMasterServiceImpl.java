package com.echo.service.impl;

import com.echo.mapper.KGCMasterMapper;
import com.echo.service.KGCMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class KGCMasterServiceImpl implements KGCMasterService {
    @Autowired
    private KGCMasterMapper kgcMasterMapper;

    @Override
    public void updateKgc(Map<String, Object> map) {
        kgcMasterMapper.updateKgc(map);
    }

    @Override
    public void updateKeyPair(Map<String,Object> map){
        kgcMasterMapper.updateKeyPair(map);
    }

    @Override
    public List<Map<String, String>> getKeyPair() {
        return kgcMasterMapper.getKeyPair();
    }

}
