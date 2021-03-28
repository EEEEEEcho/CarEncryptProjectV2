package com.echo.service.impl;

import com.echo.mapper.ServerMapper;
import com.echo.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerMapper serverMapper;

    @Override
    public void updateServer(Map<String, Object> map) {
        serverMapper.updateServer(map);
    }
}
