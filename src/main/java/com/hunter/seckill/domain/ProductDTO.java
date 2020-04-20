package com.hunter.seckill.domain;

import lombok.Data;

/**
 * @author Hunter
 * @date 2020/4/19
 */
@Data
public class ProductDTO {
    Integer id;
    String name;
    Integer count;
    Integer sale;
    Integer version;
}
