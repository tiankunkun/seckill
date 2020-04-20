package com.hunter.seckill.dao;

import com.hunter.seckill.domain.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Hunter
 * @date 2020/4/19
 */
@Mapper
@Repository
public interface ProductMapper {

    @Select("select * from product where id = #{id}")
    ProductDTO selectById(int id);

    @Update("update product set sale = sale+1 where id = #{id} and sale = #{sale}")
    int happySale(@Param("id") int id, @Param("sale") int sale);

    @Update("update product set sale = #{sale} where id = #{id}")
    int sale(@Param("id") int id, @Param("sale") int sale);
}
