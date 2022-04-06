package com.parseweb.parseweb.service.impl;

import com.parseweb.parseweb.entity.Order;
import com.parseweb.parseweb.mapper.OrderMapper;
import com.parseweb.parseweb.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhen
 * @since 2021-11-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
