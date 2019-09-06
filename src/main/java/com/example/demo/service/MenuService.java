package com.example.demo.service;

import com.example.demo.bean.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/9/6.
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RedisUtils redisUtils;
    /**
     * 这个方法加Redis缓存
     * @return
     */
    public List<Menu> getAllMenus(){
        // 从redis缓存中提取数据
        String allMenus = redisUtils.get("AllMenus");
        // 如果缓存中没有，则从数据库中查询并放入缓存中
        if(allMenus == null) {
   //         System.out.println("getUserById>>>" + "66666666666666666");
            redisUtils.set("AllMenus", String.valueOf(menuMapper.getAllMenus()));
        }
        return menuMapper.getAllMenus();
    }

}
