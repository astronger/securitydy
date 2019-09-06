package com.example.demo.config;

import com.example.demo.bean.Menu;
import com.example.demo.bean.Role;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.*;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据你的请求地址 分析出需要哪个角色，如果匹配上就返回
 * Created by Administrator on 2019/9/6.
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();  //路径匹配符（java提供）

    @Autowired
    MenuService menuService;


    /**
     * Collection集合就是你需要的角色
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl(); //请求的地址
        List<Menu> allMenus = menuService.getAllMenus(); //拿到所有的资源菜单

        for (Menu menu : allMenus) {
            if(antPathMatcher.match(menu.getPattern(),requestUrl)){ //如果匹配上了（请求的地址与pattern字段中的一样），下来看需要哪个角色
                List<Role> roles = menu.getRoles(); //拿到roles，就是你需要的角色，然后将roles转成Collection需要的东西
                String[] rolesStr = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) { //roles中东西放进rolesStr数组中
                    rolesStr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(rolesStr);
            }
        }
        //其它路径
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
