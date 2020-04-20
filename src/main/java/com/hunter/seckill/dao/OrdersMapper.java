package com.hunter.seckill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Hunter
 * @date 2020/4/19
 */
@Mapper
@Repository
public interface OrdersMapper {

    @Insert("insert into orders (product_id,product_name,create_date) values(#{productId},#{productName},#{date})")
    void insert(@Param("productId") int productId, @Param("productName") String productName, @Param("date") Date date);
}
