package com.hunter.seckill.service.impl;

import com.hunter.seckill.dao.OrdersMapper;
import com.hunter.seckill.dao.ProductMapper;
import com.hunter.seckill.domain.ProductDTO;
import com.hunter.seckill.service.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Hunter
 * @date 2020/4/19
 */
@Service
public class KillServiceImpl implements KillService {

    ProductMapper productMapper;

    OrdersMapper ordersMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setOrdersMapper(OrdersMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }

    @Transactional
    @Override
    public String sale(int id) {
        ProductDTO productDTO = productMapper.selectById(id);
        if (productDTO.getSale()>=productDTO.getCount()){
            return "库存不足，秒杀失败";
        }

        int sale = productMapper.sale(id,productDTO.getSale()+1);
        if (sale>0){
            ordersMapper.insert(productDTO.getId(),productDTO.getName(),new Date());
            return "秒杀成功";
        }
        return "秒杀失败";
    }

    @Transactional
    @Override
    public String happySale(int id) {
        //校验库存
        ProductDTO productDTO = checkStock(id);
        //更新库存
        updateSale(productDTO);
        //创建订单
        createOrder(productDTO);
        return "success";
    }

    /**
     * 校验库存
     * @param id id
     * @return ProductDTO
     */
    private ProductDTO checkStock(int id){
        ProductDTO productDTO = productMapper.selectById(id);
        if (productDTO.getSale()>=productDTO.getCount()){
            throw new RuntimeException("库存不足");
        }
        return productDTO;
    }

    private void updateSale(ProductDTO dto){
        int rows = productMapper.happySale(dto.getId(), dto.getSale());
        if (rows == 0){
            throw new RuntimeException("抢购失败，请重试");
        }
    }

    private void createOrder(ProductDTO dto){
        ordersMapper.insert(dto.getId(),dto.getName(),new Date());
    }
}
