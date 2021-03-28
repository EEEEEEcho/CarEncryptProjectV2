package com.echo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ServerMapper {
    public void updateServer(Map<String, Object> map);
}
