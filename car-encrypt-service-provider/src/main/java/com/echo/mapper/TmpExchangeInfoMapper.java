package com.echo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TmpExchangeInfoMapper {
    @Select("select server_tmp_key from tmp_exchange_info where car_vin = #{carVin}")
    public String findServerTmpKey(@Param("carVin") String carVin);

    @Insert("insert into tmp_exchange_info(car_vin,server_tmp_key) values(#{carVin},#{serverTmpKey})")
    public void insertServerTmpKey(@Param("carVin") String carVin,
                                   @Param("serverTmpKey") String serverTmpKey);
}
