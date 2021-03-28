package com.echo.mapper;

import com.echo.pojo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface CarMapper {
    public Car findCarByCarVin(@Param("carVin") String carVin);

    public void updateCarKeys(Car car);

    public void updateTheSessionKeyOfCar(Map<String, Object> map);
}
